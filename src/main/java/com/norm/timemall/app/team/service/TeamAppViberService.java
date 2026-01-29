package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppViberComment;
import com.norm.timemall.app.base.mo.AppViberPost;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchCommentPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import com.norm.timemall.app.team.domain.vo.TeamAppViberFileUploadVO;
import com.norm.timemall.app.team.domain.vo.TeamAppViberFetchOnePostVO;
import com.norm.timemall.app.team.domain.dto.TeamAppViberFileUploadDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TeamAppViberService {
    
    void publishComment(TeamAppViberPublishCommentDTO dto);

    AppViberPost findPostInfo(String postId);

    AppViberComment findOneComment(String id);

    void removeComment(AppViberComment comment);

    void commentInteract(TeamAppViberCommentInteractDTO dto);

    IPage<TeamAppViberFetchCommentPageRO> fetchComments(TeamAppViberFetchCommentPageDTO dto);

    TeamAppViberFetchOnePostVO fetchOnePost(String postId);

    IPage<TeamAppViberFetchOnePostRO> fetchFeeds(TeamAppViberFetchPostPageDTO dto);

    void removePost(AppViberPost post);

    void postInteract(TeamAppViberPostInteractDTO dto);

    TeamAppViberFileUploadVO uploadFile(TeamAppViberFileUploadDTO dto, String fileUri, MultipartFile file);

    void createPost(TeamAppViberCreatePostDTO dto,String oasisId);

}
