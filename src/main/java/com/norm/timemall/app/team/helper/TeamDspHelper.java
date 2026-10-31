package com.norm.timemall.app.team.helper;

import cn.hutool.http.HttpUtil;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.team.mapper.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Component
public class TeamDspHelper {
    @Autowired
    private TeamAppViberPostMapper teamAppViberPostMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamCoopProgramsMapper teamCoopProgramsMapper;
    @Autowired
    private TeamFlierMapper teamFlierMapper;

    @Autowired
    private TeamPhysicalProductMapper physicalProductMapper;
    @Autowired
    private TeamPhysicalOrderMapper physicalOrderMapper;

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

    public String getDigitalFlierAuthorBrandId(String flierUrl) {
        URI uri = null;
        try {
            uri = new URI(flierUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = uri.getPath();
        String flierId = FilenameUtils.getName(path);
        Flier flier = teamFlierMapper.selectById(flierId);
        return flier==null ? "" : flier.getAuthorBrandId();
    }

    public String getDefendantBrandIdFromPhysicalProductUrl(String productUrl) {
        URI uri = null;
        try {
            uri = new URI(productUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String path = uri.getPath();
        String productId = FilenameUtils.getName(path);
        PhysicalProduct product = physicalProductMapper.selectById(productId);
        return product==null ? "" : product.getSellerBrandId();
    }

    public String getDefendantBrandIdFromPhysicalOrder(String sceneUrl) {
        String orderId = HttpUtil.decodeParams(sceneUrl, StandardCharsets.UTF_8).get("order_id").getFirst();
        PhysicalOrder order = physicalOrderMapper.selectById(orderId);
        return order ==null ? "" : order.getSellerBrandId();
    }
}
