package com.norm.timemall.app.team.helper;

import com.norm.timemall.app.base.mo.AppViberPost;
import com.norm.timemall.app.base.mo.CoopPrograms;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.team.mapper.TeamAppViberPostMapper;
import com.norm.timemall.app.team.mapper.TeamCoopProgramsMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class TeamDspHelper {
    @Autowired
    private TeamAppViberPostMapper teamAppViberPostMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamCoopProgramsMapper teamCoopProgramsMapper;

    public String getViberPostOasisAdminBrandId(String postUrl){
        URI uri = null;
        try {
            uri = new URI(postUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = uri.getPath();
        String postId = FilenameUtils.getName(path);
        AppViberPost appViberPost = teamAppViberPostMapper.selectById(postId);
        if(appViberPost == null){
            return "";
        }
        Oasis oasis = teamOasisMapper.selectById(appViberPost.getOasisId());
        return oasis==null ? "" : oasis.getInitiatorId();
    }

    public String getCoopProgramAuthorBrandId(String programUrl){
        URI uri = null;
        try {
            uri = new URI(programUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = uri.getPath();
        String programId = FilenameUtils.getName(path);
        CoopPrograms program = teamCoopProgramsMapper.selectById(programId);
        return program==null ? "" : program.getAuthorBrandId();
    }

}
