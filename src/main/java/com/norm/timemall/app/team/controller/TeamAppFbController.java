package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.AppFbFeedHighlightEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppFbFeedAttachments;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchAttachmentsRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchCommentRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedRO;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamAppFbService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class TeamAppFbController {
    @Autowired
    private TeamAppFbService teamAppFbService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping(value = "/api/v1/app/feed/list")
    public TeamAppFbFetchFeedsPageVO fetchFeeds(@Validated TeamAppFbFetchFeedsPageDTO dto){

        IPage<TeamAppFbFetchFeedsPageRO> feed= teamAppFbService.findFeeds(dto);
        TeamAppFbFetchFeedsPageVO vo= new TeamAppFbFetchFeedsPageVO();
        vo.setFeed(feed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/app/feed/new")
    public SuccessVO publishNewFeed(@Validated TeamAppFbPublishNewFeedDTO dto) throws IOException {

        // validate

        if(CharSequenceUtil.isNotBlank(dto.getCtaPrimaryUrl()) && !Validator.isUrl(dto.getCtaPrimaryUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(CharSequenceUtil.isNotBlank(dto.getCtaSecondaryUrl()) && !Validator.isUrl(dto.getCtaSecondaryUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only member can post
       boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(dto.getChannel());
       if(!availablePublishFeed){
           throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
       }
       // store cover file
        String coverUrl = "";
        if( dto.getCoverFile()!=null && !dto.getCoverFile().isEmpty()){
            String fileType= FileTypeUtil.getType(dto.getCoverFile().getInputStream());
            boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
            if(notInExtensions){
                throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
            }
            coverUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getCoverFile(), FileStoreDir.FEED_COVER);
        }

        teamAppFbService.newFeed(coverUrl,dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @GetMapping("/api/open/v1/app/feed/{id}")
    public TeamAppFbFetchFeedVO fetchFeedDetail(@PathVariable("id") String id){

        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(id);
        TeamAppFbFetchFeedVO vo = new TeamAppFbFetchFeedVO();
        vo.setFeed(feed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PutMapping("/api/v1/app/feed/{id}/cover")
    public SuccessVO changeCover(@RequestParam("coverFile") MultipartFile coverFile,@PathVariable("id") String id) throws IOException {
        //validate
        if(coverFile==null || coverFile.isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(coverFile.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // only author can edit cover info
        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(id);
        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(feed==null || !currentUserBrandId.equals(feed.getAuthorBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // store file
        String coverUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(coverFile, FileStoreDir.FEED_COVER);

        // update cover url
        teamAppFbService.changeFeedCoverUrl(id,coverUrl);

        // delete unused file
        fileStoreService.deleteFile(feed.getCoverUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/feed/edit")
    public SuccessVO editFeed(@RequestBody @Validated TeamAppFbEditFeedDTO dto){

        if(CharSequenceUtil.isNotBlank(dto.getCtaPrimaryUrl()) && !Validator.isUrl(dto.getCtaPrimaryUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(CharSequenceUtil.isNotBlank(dto.getCtaSecondaryUrl()) && !Validator.isUrl(dto.getCtaSecondaryUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamAppFbService.doEditFeedInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    /**
     * open or close feed comment feature
    * */
    @PutMapping("/api/v1/app/feed/toggle_comment")
    public SuccessVO manageComment(@RequestBody @Validated TeamAppFbToggleCommentDTO dto){

        teamAppFbService.manageCommentFeature(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/feed/toggle_highlight")
    public SuccessVO highlightFeed(@RequestBody @Validated TeamAppFbHighlightFeedDTO dto){

        // only admin can execute highlight operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseFeedId(dto.getFeedId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamAppFbService.doHighlightFeed(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/app/feed/toggle_pin")
    public SuccessVO pinFeed(@RequestBody @Validated TeamAppFbPinFeedDTO dto){

        // only admin can execute highlight operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseFeedId(dto.getFeedId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamAppFbService.doPinFeed(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/feed/{id}/cover")
    public SuccessVO removeFeedCover(@PathVariable("id") String id){

        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(id);
        boolean notAuthor  = !currentUserBrandId.equals(feed.getAuthorBrandId());
        if(notAuthor){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // remove feed cover file from oss  service if exist
        fileStoreService.deleteFile(feed.getCoverUrl());
        // update feed cover url as empty
        teamAppFbService.resetFeedCover(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/app/feed/{id}")
    public SuccessVO removeFeed(@PathVariable("id") String id){

        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(id);
        // for highlight feed, only author and admin can execute remove operation

        if(feed==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean notAuthor  = !currentUserBrandId.equals(feed.getAuthorBrandId());
        boolean notAdmin = !teamDataPolicyService.validateChannelAdminRoleUseFeedId(id);
        boolean highlightFeed = AppFbFeedHighlightEnum.ON.getMark().equals(feed.getHighlight());

        if(highlightFeed && notAuthor && notAdmin){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // remove feed tb data
        // remove feed comment data
        teamAppFbService.doRemoveFeed(id);

        // remove feed cover file from oss  service if exist
        fileStoreService.deleteFile(feed.getCoverUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/app/feed_channel/{channel}/guide")
    public TeamAppFbFetchGuideVO fetchChannelGuide(@PathVariable("channel") String channel){

        return teamAppFbService.retrieveChannelGuide(channel);

    }
    @PutMapping("/api/v1/app/feed_channel/setting")
    public SuccessVO channelSetting( @RequestBody @Validated TeamAppFbChannelSettingDTO dto){

        // only admin can execute setting operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamAppFbService.doChannelSetting(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }


    @GetMapping("/api/open/v1/app/feed/comment/list")
    public TeamAppFbFetchCommentPageVO fetchComments(@Validated TeamAppFbFetchCommentPageDTO dto){

        IPage<TeamAppFbFetchCommentRO> comment = teamAppFbService.findComments(dto);
        TeamAppFbFetchCommentPageVO vo = new TeamAppFbFetchCommentPageVO();
        vo.setComment(comment);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @DeleteMapping("/api/open/v1/app/feed/comment/{id}")
    public SuccessVO removeOneComment(@PathVariable("id") String id){

        teamAppFbService.doRemoveComment(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/app/feed/comment/new")
    public SuccessVO postComment(@Validated @RequestBody TeamAppFbPostCommentDTO dto){

        teamAppFbService.doPostComment(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/open/v1/app/feed/{id}/attachments")
    public TeamAppFbFetchAttachmentsVO fbFetchAttachments(@PathVariable("id") String feedId){
        ArrayList<TeamAppFbFetchAttachmentsRO> attachments=teamAppFbService.findAttachments(feedId);
        TeamAppFbFetchAttachmentsVO vo = new TeamAppFbFetchAttachmentsVO();
        vo.setAttachments(attachments);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @DeleteMapping("/api/v1/app/feed/attachment/{id}/del")
    public SuccessVO delOneAttachment(@PathVariable("id") String id){
        AppFbFeedAttachments attachment=teamAppFbService.findOneAttachment(id);
        if(attachment==null){
            throw new QuickMessageException("未找到相关文件资料");
        }
        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(attachment.getFeedId());
        // only author and admin can execute remove operation

        if(feed==null){
            throw new QuickMessageException("未找到相关帖子资料");
        }

        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean notAuthor  = !currentUserBrandId.equals(feed.getAuthorBrandId());
        boolean notAdmin = !teamDataPolicyService.validateChannelAdminRoleUseFeedId(id);

        if(notAuthor && notAdmin){
            throw new QuickMessageException("权限校验不通过");
        }
        // remove from db
        teamAppFbService.removeAttachment(id);
        // remove from oss
        fileStoreService.deleteFile(attachment.getFileUri());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/app/feed/attachment/add")
    public SuccessVO addAttachment(@Validated TeamAppFbNewAttachmentDTO dto) throws IOException {
        if( dto.getAttachment()==null || dto.getAttachment().isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        TeamAppFbFetchFeedRO feed = teamAppFbService.retrieveOneFeedInfo(dto.getFeedId());
        // only author  can execute  operation
        if(feed==null){
            throw new QuickMessageException("未找到相关帖子资料");
        }
        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean notAuthor  = !currentUserBrandId.equals(feed.getAuthorBrandId());
        if(notAuthor ){
            throw new QuickMessageException("权限校验不通过");
        }
        // store file to oss
        String fileUri=fileStoreService.storeWithLimitedAccess(dto.getAttachment(),FileStoreDir.FEED_ATTACHMENTS);
        String fileName= dto.getAttachment().getOriginalFilename();
        String fileType= "";
        if (fileName != null) {
            fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        }

        // store to db
        teamAppFbService.addOneAttachment(dto.getFeedId(),fileUri,fileType,fileName);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
