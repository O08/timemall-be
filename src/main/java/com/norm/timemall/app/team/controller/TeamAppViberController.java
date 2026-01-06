package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.AppViberFileSceneEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppViberComment;
import com.norm.timemall.app.base.mo.AppViberPost;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.ms.service.MsGroupMemberRelService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchCommentPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamAppViberService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppViberController {
    @Autowired
    private TeamAppViberService teamAppViberService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private MsGroupMemberRelService msGroupMemberRelService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TeamOasisChannelService teamOasisChannelService;


    @PostMapping("/api/v1/app/viber/feed/post/create")
    public SuccessVO createPost(@Validated @RequestBody TeamAppViberCreatePostDTO dto) {

        // if author mute，can't create post
        FetchOneOasisChannelGeneralInfoRO channelInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(dto.getChannel());

        if(channelInfo==null){
            throw new QuickMessageException("未找到相关频道数据");
        }

        boolean enableRW = msGroupMemberRelService.haveReadAndWriteForOasis(channelInfo.getOasisId());
        if(!enableRW){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamAppViberService.createPost(dto, channelInfo.getOasisId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/app/viber/feed/comment/getList")
    public TeamAppViberFetchCommentPageVO fetchComments(TeamAppViberFetchCommentPageDTO dto) {
        IPage<TeamAppViberFetchCommentPageRO> comments = teamAppViberService.fetchComments(dto);
        TeamAppViberFetchCommentPageVO vo =new TeamAppViberFetchCommentPageVO();
        vo.setComment(comments);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }

    @PostMapping("/api/v1/app/viber/feed/comment/publish")
    public SuccessVO publishComment(@Validated @RequestBody TeamAppViberPublishCommentDTO dto) {
        // query post info
        AppViberPost post = teamAppViberService.findPostInfo(dto.getPostId());
        if(post == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // if author mute，can't create post
        boolean enableRW = msGroupMemberRelService.haveReadAndWriteForOasis(post.getOasisId());
        if(!enableRW){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        
        teamAppViberService.publishComment(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
        
    }

    @DeleteMapping("/api/v1/app/viber/feed/comment/{id}/del")
    public SuccessVO deleteComment(@PathVariable("id") String id) {
        // Find comment
        AppViberComment comment = teamAppViberService.findOneComment(id);
        if(comment == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // Find post
        AppViberPost post = teamAppViberService.findPostInfo(comment.getPostId());
        if(post == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // only member can del post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(post.getChannel());
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // Remove comment
        teamAppViberService.removeComment(comment);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/app/viber/feed/comment/interact")
    public SuccessVO commentInteract(@Validated @RequestBody TeamAppViberCommentInteractDTO dto) {
        
        teamAppViberService.commentInteract(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/app/viber/feed/{id}/get")
    public TeamAppViberFetchOnePostVO fetchOnePost(@PathVariable("id") String id) {
        return teamAppViberService.fetchOnePost(id);
    }

    @GetMapping("/api/v1/app/viber/feed/getFeed")
    public TeamAppViberFetchPostPageVO fetchFeeds(TeamAppViberFetchPostPageDTO dto){
        // only member can fetch post
        boolean hasPermission = teamDataPolicyService.alreadyOasisMember(dto.getChannel());
        if(!hasPermission){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        IPage<TeamAppViberFetchOnePostRO> feed = teamAppViberService.fetchFeeds(dto);
        TeamAppViberFetchPostPageVO vo = new TeamAppViberFetchPostPageVO();
        vo.setFeed(feed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/app/viber/feed/post/interact")
    public SuccessVO postInteract(@Validated @RequestBody TeamAppViberPostInteractDTO dto) {
        
        teamAppViberService.postInteract(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/app/viber/feed/post/{id}/del")
    public SuccessVO deletePost(@PathVariable("id") String id) {
        // Find post
        AppViberPost post = teamAppViberService.findPostInfo(id);
        if(post == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // Check if current user is the author of the post or has admin rights
        String currentUserId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isAuthor = currentUserId.equals(post.getAuthorBrandId());
        boolean isAdmin = teamDataPolicyService.validateChannelAdminRoleUseChannelId(post.getChannel());
        
        if (!isAuthor && !isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // Remove post
        teamAppViberService.removePost(post);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/app/viber/feed/file/upload")
    public TeamAppViberFileUploadVO uploadFile(@Validated TeamAppViberFileUploadDTO dto) throws IOException {

        if(dto.getFile()==null || dto.getFile().isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // only member can create post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(dto.getChannel());
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // validate file type
        String fileType= FileTypeUtil.getType(dto.getFile().getInputStream());


        // store file
        String fileUri="";
        if (AppViberFileSceneEnum.IMAGE.getMark().equals(dto.getScene())) {

            boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
            if(notInExtensions){
                throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
            }
            // For image files, process as avif if needed
            fileUri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getFile(), FileStoreDir.VIBER_FILE);
        }

        if(AppViberFileSceneEnum.ATTACHMENT.getMark().equals(dto.getScene())){
            fileUri = fileStoreService.storeWithUnlimitedAccess(dto.getFile(), FileStoreDir.VIBER_FILE);
        }

        return teamAppViberService.uploadFile(dto,fileUri,dto.getFile());
    }

    @PutMapping("/api/v1/app/viber/{channel}/user/{id}/mute")
    public SuccessVO muteUser(@PathVariable("channel") String channel, @PathVariable("id") String userId) {

        boolean isAdmin = teamDataPolicyService.validateChannelAdminRoleUseChannelId(channel);
        boolean beAdminUserId= SecurityUserHelper.getCurrentPrincipal().getUserId().equals(userId);

        if(!isAdmin || beAdminUserId){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(channel);
        if(channelGeneralInfo==null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }


        msGroupMemberRelService.banOneUser(channelGeneralInfo.getOasisId(), userId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
