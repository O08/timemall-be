package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBankDTO;
import com.norm.timemall.app.studio.domain.dto.StudioContactDTO;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商家设置
 */
@RestController
public class StudioBrandSettingController {

    @Autowired
    private StudioBrandService brandService;

    @Autowired
    private FileStoreService fileStoreService;


    /**
     *
     *银行账号设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/pay_setting/bank")
    public SuccessVO settingBrandBank(@PathVariable("brand_id") String brandId,
                                      @AuthenticationPrincipal CustomizeUser user,
                                      @Validated @RequestBody  StudioBrandBankDTO dto)
    {
        brandService.modifyBrandBank(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     *支付宝支付设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/pay_setting/ali_pay")
    public SuccessVO settingAliPay(@PathVariable("brand_id") String brandId,
                                   @AuthenticationPrincipal CustomizeUser user,
                                   @RequestParam("file") MultipartFile file)
    {
        // 查询
        Brand brand = brandService.findbyId(brandId);
        // 检查数据是否准许入库: 用户数据与商家数据不一致 拦截
        if (brand == null || (!user.getUserId().equals(brand.getCustomerId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // 存储图片
        String uri = fileStoreService.storeWithSpecifiedDir(file, FileStoreDir.PAY_ALI);
        // 更新
        brandService.modifyAliPay(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getAlipay());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     *联系方式设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/contact_setting")
    public SuccessVO settingBrandContact(@PathVariable("brand_id") String brandId, @RequestBody StudioContactDTO contact)
    {
        brandService.modifyBrandContact(brandId,contact);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     *支付宝支付设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/pay_setting/wechat_pay")
    public SuccessVO settingWechatPay(@PathVariable("brand_id") String brandId,
                                      @AuthenticationPrincipal CustomizeUser user,
                                      @RequestParam("file") MultipartFile file)
    {
        // 查询
        Brand brand = brandService.findbyId(brandId);
        // 检查数据是否准许入库: 用户数据与商家数据不一致 拦截
        if (brand == null || (!user.getUserId().equals(brand.getCustomerId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // 存储图片
        String uri = fileStoreService.storeWithSpecifiedDir(file, FileStoreDir.PAY_WECAHAT);
        // 更新
        brandService.modifyWeChatPay(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getWechatpay());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     *封面设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/cover")
    public SuccessVO settingCover(@PathVariable("brand_id") String brandId,
                                  @AuthenticationPrincipal CustomizeUser user,
                                  @RequestParam("file") MultipartFile file)
    {
        // 查询
        Brand brand = brandService.findbyId(brandId);
        // 检查数据是否准许入库: 用户数据与商家数据不一致 拦截
        if (brand == null || (!user.getUserId().equals(brand.getCustomerId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // 存储图片
        String uri = fileStoreService.storeWithSpecifiedDir(file, FileStoreDir.BRAND_COVER);
        // 更新
        brandService.modifyBrandCover(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getCover());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     *
     *头像设置
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/avator")
    public SuccessVO settingAvator(@PathVariable("brand_id") String brandId,
                                   @AuthenticationPrincipal CustomizeUser user,
                                   @RequestParam("file") MultipartFile file)
    {
        // 查询
        Brand brand = brandService.findbyId(brandId);
        // 检查数据是否准许入库: 用户数据与商家数据不一致 拦截
        if (brand == null || (!user.getUserId().equals(brand.getCustomerId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // 存储图片
        String uri = fileStoreService.storeWithSpecifiedDir(file, FileStoreDir.BRAND_AVATOR);
        // 更新
        brandService.modifyBrandAvatar(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getAvator());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
