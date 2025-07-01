package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.pojo.BrandInfo;
import com.norm.timemall.app.studio.domain.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface StudioBrandService {
    void modifyBrandProfile(String brandId, String userId,StudioBrandProfileDTO dto);

    void modifyBrandBank(String brandId, String userId,StudioBrandBankDTO dto);

    void modifyBrandContact(String brandId, StudioContactDTO contact);

    void modifyAliPay(String brandId, String uri);

    Brand findbyId(String brandId);

    void modifyWeChatPay(String brandId, String uri);

    void modifyBrandCover(String brandId, String uri);

    void modifyBrandAvatar(String brandId, String uri);

    BrandInfo findBrandInfoByUserId(String userId);

    void modifyBrandWechatQr(String brandId, String uri);

    void modifyBrandBasic(String brandId, String userId, StudioBrandBasicInfoDTO dto);

    void modifyBrandSkills(String brandId, String userId, StudioBrandSkillsDTO dto);

    void modifyBrandExperience(String brandId, String userId, StudioBrandExperienceDTO dto);

    void modifyBrandLinks(StudioBrandLinksDTO dto);

    void settingStudioConfig(ModifyBrandStudioConfigDTO dto);
}
