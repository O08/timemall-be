package com.norm.timemall.app.studio.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
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

import java.io.IOException;

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
        String uri = fileStoreService.storeWithLimitedAccess(file, FileStoreDir.PAY_ALI);
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
    @PutMapping(value = "/api/v1/web_estudio/brand/contact_setting")
    public SuccessVO settingBrandContact(@RequestBody StudioContactDTO contact)
    {
        brandService.modifyBrandContact(contact);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     *微信支付设置
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
        String uri = fileStoreService.storeWithLimitedAccess(file, FileStoreDir.PAY_WECAHAT);
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
        String uri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(file, FileStoreDir.BRAND_COVER);
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
        String uri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(file, FileStoreDir.BRAND_AVATOR);
        // 更新
        brandService.modifyBrandAvatar(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getAvator());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     *
     *微信二维码
     * @param brandId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/wechat_qrcode")
    public SuccessVO settingWechatQrCode(@PathVariable("brand_id") String brandId,
                                         @AuthenticationPrincipal CustomizeUser user,
                                         @RequestParam("file") MultipartFile file){
        // 查询
        Brand brand = brandService.findbyId(brandId);
        // 检查数据是否准许入库: 用户数据与商家数据不一致 拦截
        if (brand == null || (!user.getUserId().equals(brand.getCustomerId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // 存储图片
        String uri = fileStoreService.storeWithLimitedAccess(file, FileStoreDir.WECHAT_QR);
        // 更新
        brandService.modifyBrandWechatQr(brandId,uri);
        // 删除不再使用文件数据
        fileStoreService.deleteFile(brand.getWechat());
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping(value = "/api/v1/web_estudio/brand/setting/resume")
    public SuccessVO settingBrandResume(@RequestParam("material") MultipartFile resumeMaterial) throws IOException {
        // 空文件校验
        if (resumeMaterial==null || resumeMaterial.isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // 大小限制校验: 最大 10M
        if (resumeMaterial.getSize() > 10 * 1024 * 1024){
            throw new ErrorCodeException(CodeEnum.FILE_SIZE_EXCEED_LIMIT);
        }
        // 格式校验: 通过文件头识别真实类型，仅支持 pdf
        String fileType = FileTypeUtil.getType(resumeMaterial.getInputStream());
        if (!"pdf".equals(fileType)){
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }
        // 当前登录用户简历信息
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Brand brand = brandService.findbyId(brandId);

        // 存储简历文件
        String uri = fileStoreService.storeWithLimitedAccess(resumeMaterial, FileStoreDir.BRAND_RESUME);
        // 更新
        brandService.modifyBrandResume(brandId,uri);
        // 删除已上传的过时简历文件
        fileStoreService.deleteFile(brand.getResumeUrl());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/web_estudio/brand/setting/remove_resume")
    public SuccessVO removeBrandResume(){
        // 当前登录商家信息
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Brand brand = brandService.findbyId(brandId);
        if(CharSequenceUtil.isBlank(brand.getResumeUrl())){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // 删除已上传的简历文件
        fileStoreService.deleteFile(brand.getResumeUrl());
        // 清空简历信息
        brandService.clearBrandResume(brandId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
