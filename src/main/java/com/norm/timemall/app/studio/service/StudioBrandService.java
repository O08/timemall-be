package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.pojo.BrandInfo;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBankDTO;
import com.norm.timemall.app.studio.domain.dto.StudioBrandProfileDTO;
import com.norm.timemall.app.studio.domain.dto.StudioContactDTO;
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

}
