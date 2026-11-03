package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OasisInvitationLinkExpireTimeTypeEnum;
import com.norm.timemall.app.base.enums.OasisRoleCoreEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OasisInvitationLink;
import com.norm.timemall.app.base.mo.OasisRole;
import com.norm.timemall.app.team.domain.dto.TeamCreateOasisInvitationLinkDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkInfoDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkPageRO;
import com.norm.timemall.app.team.mapper.TeamOasisInvitationLinkMapper;
import com.norm.timemall.app.team.mapper.TeamOasisRoleMapper;
import com.norm.timemall.app.team.service.TeamOasisInvitationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOasisInvitationLinkServiceImpl implements TeamOasisInvitationLinkService {
    @Autowired
    private TeamOasisInvitationLinkMapper teamOasisInvitationLinkMapper;

    @Autowired
    private TeamOasisRoleMapper teamOasisRoleMapper;

    @Override
    public void createInvitationLink(TeamCreateOasisInvitationLinkDTO dto) {

        // Validate that the role exists and belongs to the oasis
        OasisRole role = teamOasisRoleMapper.selectById(dto.getGrantedOasisRoleId());
        if(role==null){
            throw new QuickMessageException("未找到相关身份组数据");
        }
        if(!dto.getOasisId().equals(role.getOasisId())){
            throw new QuickMessageException("身份组校验失败");
        }

        if(OasisRoleCoreEnum.ADMIN.getMark().equals(role.getRoleCode())){
            throw new QuickMessageException("管理员身份组暂不支持授权");
        }

        // Generate unique invitation code
        String invitationCode = generateUniqueInvitationCode();
        
        // Calculate expiration time based on the enum value
        OasisInvitationLinkExpireTimeTypeEnum expireType = OasisInvitationLinkExpireTimeTypeEnum.findType(dto.getExpireTime());
        Date expireTime = calculateExpirationTime(expireType);
        
        // Create the invitation link entity
        OasisInvitationLink invitationLink = new OasisInvitationLink();
        invitationLink.setId(IdUtil.simpleUUID())
                .setInviterBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setOasisId(dto.getOasisId())
                .setInvitationCode(invitationCode)
                .setGrantedOasisRoleId(dto.getGrantedOasisRoleId())
                .setGrantedOasisRoleName(role.getRoleName())
                .setExpireTime(expireTime)
                .setMaxUses(dto.getMaxUses())
                .setUsageCount(0) // Initial usage count is 0
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        
        // Save to database
        teamOasisInvitationLinkMapper.insert(invitationLink);

    }
    
    @Override
    public IPage<TeamQueryInvitationLinkPageRO> queryInvitationLink(TeamQueryInvitationLinkPageDTO dto) {
        IPage<TeamQueryInvitationLinkPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        
        return teamOasisInvitationLinkMapper.selectPageByOasisId(page, dto.getOasisId());
    }
    
    @Override
    public TeamQueryInvitationLinkInfoRO queryInvitationLinkInfo(TeamQueryInvitationLinkInfoDTO dto) {

        return teamOasisInvitationLinkMapper.selectInvitationLinkInfo( dto.getInvitationCode());

    }

    @Override
    public OasisInvitationLink findOneInvitationLink(String id) {
        return teamOasisInvitationLinkMapper.selectById(id);
    }

    @Override
    public void delInvitationLink(String id) {
        teamOasisInvitationLinkMapper.deleteById(id);
    }
    
    @Override
    public void autoIncrementUsageCount(String id) {
        teamOasisInvitationLinkMapper.autoIncrementUsageCount(id);
    }

    /**
     * Generate a unique invitation code
     */
    private String generateUniqueInvitationCode() {
        String code;
        int retries = 0;
        do {
            // Generates 8-character random string (A-Z, 0-9)
            code = RandomUtil.randomString(8).toUpperCase();
            retries++;

            // Safety break after 10 tries to prevent hanging
            if (retries > 10) {
                throw new ErrorCodeException(CodeEnum.FAILED);
            }
        } while (isCodeTaken(code));

        return code;
    }

    private boolean isCodeTaken(String code) {
        return teamOasisInvitationLinkMapper.exists(
                Wrappers.lambdaQuery(OasisInvitationLink.class)
                        .eq(OasisInvitationLink::getInvitationCode, code)
        );
    }


    /**
     * Calculate expiration time based on the enum value
     */
    private Date calculateExpirationTime(OasisInvitationLinkExpireTimeTypeEnum expireType) {
        Date currentTime = new Date();

        return switch (expireType) {
            case ONE_HOUR -> DateUtil.offsetHour(currentTime, 1);
            case ONE_DAY -> DateUtil.offsetDay(currentTime, 1);
            case SEVEN_DAYS -> DateUtil.offsetDay(currentTime, 7);
            case ONE_MONTH -> DateUtil.offsetMonth(currentTime, 1);
            case SIX_MONTHS -> DateUtil.offsetMonth(currentTime, 6);
            case ONE_YEAR -> DateUtil.offsetMonth(currentTime, 12);
            case null -> throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        };
    }
}
