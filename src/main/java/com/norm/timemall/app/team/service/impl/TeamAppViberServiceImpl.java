package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.AppViberPostEmbedFacetEnum;
import com.norm.timemall.app.base.enums.AppViberPostFileStautsEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppViberComment;
import com.norm.timemall.app.base.mo.AppViberCommentInteract;
import com.norm.timemall.app.base.mo.AppViberPost;
import com.norm.timemall.app.base.mo.AppViberPostInteract;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamAppViberCommentInteractionHelper;
import com.norm.timemall.app.team.domain.pojo.TeamAppViberPostInteractionHelper;
import com.norm.timemall.app.team.domain.pojo.TeamAppViberPostEmbed;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchCommentPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import com.norm.timemall.app.team.domain.vo.TeamAppViberFetchOnePostVO;
import com.norm.timemall.app.team.mapper.TeamAppViberCommentInteractMapper;
import com.norm.timemall.app.team.mapper.TeamAppViberCommentMapper;
import com.norm.timemall.app.team.mapper.TeamAppViberPostInteractMapper;
import com.norm.timemall.app.team.mapper.TeamAppViberPostMapper;
import com.norm.timemall.app.base.mo.AppViberPostFile;
import com.norm.timemall.app.team.domain.vo.TeamAppViberFileUploadVO;
import com.norm.timemall.app.team.mapper.TeamAppViberPostFileMapper;
import com.norm.timemall.app.team.service.TeamAppViberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamAppViberServiceImpl implements TeamAppViberService {

    @Autowired
    private TeamAppViberPostMapper teamAppViberPostMapper;

    @Autowired
    private TeamAppViberCommentMapper teamAppViberCommentMapper;

    @Autowired
    private TeamAppViberCommentInteractMapper teamAppViberCommentInteractMapper;

    @Autowired
    private TeamAppViberPostInteractMapper teamAppViberPostInteractMapper;

    @Autowired
    private TeamAppViberPostFileMapper teamAppViberPostFileMapper;



    @Override
    public void publishComment(TeamAppViberPublishCommentDTO dto) {
        String authorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // Create and save comment
        AppViberComment comment = new AppViberComment();
        comment.setId(IdUtil.simpleUUID())
                .setPostId(dto.getPostId())
                .setTextMsg(dto.getTextMsg())
                .setAuthorBrandId(authorBrandId)
                .setLikes(0)
                .setDiss(0)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppViberCommentMapper.insert(comment);

        // Update post comment count
        teamAppViberPostMapper.updateComments(dto.getPostId(), 1);
    }

    @Override
    public AppViberPost findPostInfo(String postId) {
        return teamAppViberPostMapper.selectById(postId);
    }

    @Override
    public AppViberComment findOneComment(String id) {
        return teamAppViberCommentMapper.selectById(id);
    }

    @Override
    public void removeComment(AppViberComment comment) {

        // Delete comment
        teamAppViberCommentMapper.deleteById(comment.getId());

        // Delete comment interact (all likes/dislikes for this comment)
        LambdaQueryWrapper<AppViberCommentInteract> interactWrapper = Wrappers.lambdaQuery();
        interactWrapper.eq(AppViberCommentInteract::getCommentId, comment.getId());
        teamAppViberCommentInteractMapper.delete(interactWrapper);

        // Update post comment count
        teamAppViberPostMapper.updateComments(comment.getPostId(), -1);

    }

    @Override
    public void commentInteract(TeamAppViberCommentInteractDTO dto) {

        // Get comment for updating counts
        AppViberComment comment = teamAppViberCommentMapper.selectById(dto.getCid());
        if(comment == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // Find existing interact record
        LambdaQueryWrapper<AppViberCommentInteract> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppViberCommentInteract::getCommentId, dto.getCid())
                .eq(AppViberCommentInteract::getReaderBrandId, currentUserBrandId);
        AppViberCommentInteract existingInteract = teamAppViberCommentInteractMapper.selectOne(wrapper);

        if(existingInteract != null && dto.getEvent().equals(existingInteract.getLikeAction().toString())){
            return;
        }

        TeamAppViberCommentInteractionHelper interactionDelta = TeamAppViberCommentInteractionHelper.calculateDeltas(existingInteract, dto);

        // Update comment like/diss counts
        LambdaQueryWrapper<AppViberComment> commentWrapper = Wrappers.lambdaQuery();
        commentWrapper.eq(AppViberComment::getId, dto.getCid());

        AppViberComment updateComment = new AppViberComment();
        updateComment.setId(dto.getCid())
                .setLikes(comment.getLikes() + interactionDelta.getLikeDelta())
                .setDiss(comment.getDiss() + interactionDelta.getDissDelta())
                .setModifiedAt(new Date());

        teamAppViberCommentMapper.update(updateComment, commentWrapper);

        // Del old action
        if(existingInteract != null) {
            // Delete old interact record
            teamAppViberCommentInteractMapper.deleteById(existingInteract.getId());
        }

        // Insert new interact record
        AppViberCommentInteract interact = new AppViberCommentInteract();
        interact.setId(IdUtil.simpleUUID())
                .setCommentId(dto.getCid())
                .setPostId(comment.getPostId())
                .setReaderBrandId(currentUserBrandId)
                .setLikeAction(Integer.valueOf(dto.getEvent()));
        teamAppViberCommentInteractMapper.insert(interact);


    }

    @Override
    public IPage<TeamAppViberFetchCommentPageRO> fetchComments(TeamAppViberFetchCommentPageDTO dto) {
        IPage<TeamAppViberFetchCommentPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return teamAppViberCommentMapper.selectPageByPostId(page, dto, currentUserBrandId);
    }

    @Override
    public IPage<TeamAppViberFetchOnePostRO> fetchFeeds(TeamAppViberFetchPostPageDTO dto) {
        IPage<TeamAppViberFetchOnePostRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppViberPostMapper.selectPostPage(page, dto);
    }

    @Override
    public TeamAppViberFetchOnePostVO fetchOnePost(String postId) {
        TeamAppViberFetchOnePostRO postRO=teamAppViberPostMapper.selectPostInfoById(postId);
        TeamAppViberFetchOnePostVO vo = new TeamAppViberFetchOnePostVO();
        vo.setPost(postRO);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void removePost(AppViberPost post) {
        // Delete the post itself
        teamAppViberPostMapper.deleteById(post.getId());

        // delete all post interactions
        LambdaQueryWrapper<AppViberPostInteract> postInteractWrapper = Wrappers.lambdaQuery();
        postInteractWrapper.eq(AppViberPostInteract::getPostId, post.getId());
        teamAppViberPostInteractMapper.delete(postInteractWrapper);

        // Delete all comments related to this post
        LambdaQueryWrapper<AppViberComment> commentWrapper = Wrappers.lambdaQuery();
        commentWrapper.eq(AppViberComment::getPostId, post.getId());
        teamAppViberCommentMapper.delete(commentWrapper);
        
        // Delete all comment interactions related to this post
        LambdaQueryWrapper<AppViberCommentInteract> commentInteractWrapper = Wrappers.lambdaQuery();
        commentInteractWrapper.eq(AppViberCommentInteract::getPostId,post.getId());
        teamAppViberCommentInteractMapper.delete(commentInteractWrapper);

        // mark files as unused
        if(post.getEmbed()!=null){
            TeamAppViberPostEmbed postEmbed = new Gson().fromJson(post.getEmbed().toString(), TeamAppViberPostEmbed.class);
            markFileAsInUnUse(postEmbed);
        }


    }

    private void markFileAsInUnUse(TeamAppViberPostEmbed embed){
        List<String> fileLinks=new ArrayList<>();
        if(embed!=null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode())){
            embed.getAttachments().forEach(a->{
                fileLinks.add(a.getUrl());
            });
        }
        if(embed!=null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.IMAGE.getCode())){
            embed.getImages().forEach(a->{
                fileLinks.add(a.getLink());
            });
        }

        ListUtil.partition(fileLinks,5).forEach(e->{
            teamAppViberPostFileMapper.batchUpdateStatusByLink(e, AppViberPostFileStautsEnum.UNUSED.getCode());
        });

    }


    @Override
    public void postInteract(TeamAppViberPostInteractDTO dto) {

        // Get post for updating counts
        AppViberPost post = teamAppViberPostMapper.selectById(dto.getPostId());
        if(post == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // Find existing interact record
        LambdaQueryWrapper<AppViberPostInteract> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppViberPostInteract::getPostId, dto.getPostId())
                .eq(AppViberPostInteract::getReaderBrandId, currentUserBrandId);
        AppViberPostInteract existingInteract = teamAppViberPostInteractMapper.selectOne(wrapper);

        // Check if the event is the same as existing interaction
        boolean denyAction = TeamAppViberPostInteractionHelper.isRepeatOrNotAllowAction(existingInteract, dto);
        if(denyAction){
            return;
        }

        TeamAppViberPostInteractionHelper interactionDelta = TeamAppViberPostInteractionHelper.calculateDeltas(existingInteract, dto);

        // Update post like/share counts
        if (interactionDelta.getLikeDelta() != 0) {
            teamAppViberPostMapper.updateLikes(dto.getPostId(), interactionDelta.getLikeDelta());
        }
        if (interactionDelta.getShareDelta() != 0) {
            teamAppViberPostMapper.updateShares(dto.getPostId(), interactionDelta.getShareDelta());
        }

        // Delete old action if exists
        if(existingInteract != null) {
            // Delete old interact record
            teamAppViberPostInteractMapper.deleteById(existingInteract.getId());
        }

        // Insert new interact record
        AppViberPostInteract interact = TeamAppViberPostInteractionHelper.createInteract(dto);
        teamAppViberPostInteractMapper.insert(interact);

    }

    @Override
    public TeamAppViberFileUploadVO uploadFile(TeamAppViberFileUploadDTO dto, String fileUri, MultipartFile file){

        // Create and save file record to database
        AppViberPostFile fileRecord = new AppViberPostFile();
        fileRecord.setId(IdUtil.simpleUUID())
                .setChannel(dto.getChannel())
                .setScene(dto.getScene())
                .setLink(fileUri)
                .setFileName(file.getOriginalFilename())
                .setContentType(file.getContentType())
                .setStatus(AppViberPostFileStautsEnum.UNUSED.getCode())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppViberPostFileMapper.insert(fileRecord);

        TeamAppViberFileUploadVO vo  = new TeamAppViberFileUploadVO();
        vo.setLink(fileUri);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;
    }

    @Override
    public void createPost(TeamAppViberCreatePostDTO dto,String oasisId) {
        // rule check
        createPostRuleCheck(dto);
        // insert new record
        String authorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        AppViberPost post = new AppViberPost();
        post.setId(IdUtil.simpleUUID())
                .setChannel(dto.getChannel())
                .setOasisId(oasisId)
                .setAuthorBrandId(authorBrandId)
                .setComments(0)
                .setShares(0)
                .setLikes(0)
                .setTextMsg(dto.getTextMsg())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        if(dto.getEmbed()!=null){
            post.setEmbed(new Gson().toJson(dto.getEmbed()));
        }
        teamAppViberPostMapper.insert(post);

        markFileAsInService(dto);
    }

    private void markFileAsInService(TeamAppViberCreatePostDTO dto){
        TeamAppViberPostEmbed embed = dto.getEmbed();
        List<String> fileLinks=new ArrayList<>();
        if(dto.getEmbed()!=null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode())){
            embed.getAttachments().forEach(a->{
                fileLinks.add(a.getUrl());
            });
        }
        if(dto.getEmbed()!=null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.IMAGE.getCode())){
            embed.getImages().forEach(a->{
                fileLinks.add(a.getLink());
            });
        }

        ListUtil.partition(fileLinks,5).forEach(e->{
            teamAppViberPostFileMapper.batchUpdateStatusByLink(e, AppViberPostFileStautsEnum.IN_SERVICE.getCode());
        });

    }

    private void createPostRuleCheck(TeamAppViberCreatePostDTO dto){
        createPostEmptyCheck(dto);
        createPostSizeCheck(dto);
    }

    private void  createPostEmptyCheck(TeamAppViberCreatePostDTO dto){
        TeamAppViberPostEmbed embed = dto.getEmbed();
        boolean isEmptyTextMsgAndEmbed=CharSequenceUtil.isBlank(dto.getTextMsg()) && embed==null;

        boolean facetNotValid=embed!=null && !AppViberPostEmbedFacetEnum.validation(embed.getFacet());

        boolean attachmentPostIsEmpty=embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode())
                && (embed.getAttachments()==null || embed.getAttachments().isEmpty());

        boolean imagePostIsEmpty=embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.IMAGE.getCode())
                && (embed.getImages()==null || embed.getImages().isEmpty());

        boolean thirdPartyImagePostIsEmpty=embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.THIRD_PARTY_IMAGE.getCode())
                && (embed.getImages()==null || embed.getImages().isEmpty());

        boolean videoPostIsEmpty=embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.VIDEO.getCode())
                && (embed.getVideos()==null || embed.getVideos().isEmpty());

        boolean linkPostIsEmpty=embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.LINK.getCode())
                && (embed.getLinks()==null || embed.getLinks().isEmpty());



        if(isEmptyTextMsgAndEmbed || facetNotValid || attachmentPostIsEmpty || imagePostIsEmpty
                || thirdPartyImagePostIsEmpty || videoPostIsEmpty || linkPostIsEmpty)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }
    private void createPostSizeCheck(TeamAppViberCreatePostDTO dto){
        TeamAppViberPostEmbed embed = dto.getEmbed();
        
        boolean attachmentPostExceedsLimit = embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode())
                && (embed.getAttachments() != null && embed.getAttachments().size() >= 10);

        boolean imagePostExceedsLimit = embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.IMAGE.getCode())
                && (embed.getImages() != null && embed.getImages().size() >= 50);

        boolean thirdPartyImagePostExceedsLimit = embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.THIRD_PARTY_IMAGE.getCode())
                && (embed.getImages() != null && embed.getImages().size() >= 50);

        boolean videoPostExceedsLimit = embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.VIDEO.getCode())
                && (embed.getVideos() != null && embed.getVideos().size() >= 2);

        boolean linkPostExceedsLimit = embed != null && embed.getFacet().equals(AppViberPostEmbedFacetEnum.LINK.getCode())
                && (embed.getLinks() != null && embed.getLinks().size() >= 2);

        if (attachmentPostExceedsLimit || imagePostExceedsLimit || thirdPartyImagePostExceedsLimit
                || videoPostExceedsLimit || linkPostExceedsLimit) {
            throw new ErrorCodeException(CodeEnum.MAX_LIMIT);
        }
    }


}
