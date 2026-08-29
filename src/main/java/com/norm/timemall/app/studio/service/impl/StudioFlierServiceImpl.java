package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.Flier;
import com.norm.timemall.app.base.mo.FlierCopy;
import com.norm.timemall.app.base.mo.FlierVisitor;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.studio.domain.dto.StudioCreateFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioEditFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandCreatedFlierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchReceiverFlierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFlierVisitorInfoDTO;
import com.norm.timemall.app.studio.domain.dto.StudioHandoutFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioInteractFlierDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandCreatedFlierPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchReceiverFlierPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFlierVisitorInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCreatedFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchReceiverFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFlierVisitorInfoVO;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.mapper.StudioFlierCopyMapper;
import com.norm.timemall.app.studio.mapper.StudioFlierMapper;
import com.norm.timemall.app.studio.mapper.StudioFlierVisitorMapper;
import com.norm.timemall.app.studio.service.StudioFlierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioFlierServiceImpl implements StudioFlierService {

    @Autowired
    private StudioFlierMapper studioFlierMapper;

    @Autowired
    private StudioFlierCopyMapper studioFlierCopyMapper;

    @Autowired
    private StudioFlierVisitorMapper studioFlierVisitorMapper;

    @Autowired
    private BaseElectricityService baseElectricityService;

    @Autowired
    private StudioBrandMapper studioBrandMapper;

    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;


    @Override
    public StudioFetchBrandCreatedFlierPageVO findBrandCreatedFlierPage(StudioFetchBrandCreatedFlierPageDTO dto) {
        Page<StudioFetchBrandCreatedFlierPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        String authorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<StudioFetchBrandCreatedFlierPageRO> flierPage = studioFlierMapper.selectBrandCreatedFlierPage(page, dto, authorBrandId);

        StudioFetchBrandCreatedFlierPageVO vo = new StudioFetchBrandCreatedFlierPageVO();
        vo.setFlier(flierPage);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void createFlier(StudioCreateFlierDTO dto, String contentLink) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Flier flier = new Flier()
                .setId(IdUtil.simpleUUID())
                .setAuthorBrandId(brandId)
                .setTitle(dto.getTitle())
                .setDescription(dto.getDescription())
                .setCtaLink(dto.getCtaLink())
                .setContentLink(contentLink)
                .setLikes(0)
                .setCopies(0)
                .setCtaClicks(0)
                .setStatus(FlierStatusEnum.NORMAL.getValue())
                .setCreatedAt(new Date())
                .setModifiedAt(new Date());

        studioFlierMapper.insert(flier);
    }

    @Override
    public void editFlier(StudioEditFlierDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Flier existing = studioFlierMapper.selectById(dto.getFlierId());
        if (existing == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if ( !existing.getAuthorBrandId().equals(brandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        if (!FlierStatusEnum.NORMAL.getValue().equals(existing.getStatus())) {
            throw new QuickMessageException("传单状态不支持该操作");
        }

        LambdaUpdateWrapper<Flier> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Flier::getId, dto.getFlierId())
                .set(Flier::getTitle, dto.getTitle())
                .set(Flier::getDescription, dto.getDescription())
                .set(Flier::getCtaLink, dto.getCtaLink())
                .set(Flier::getModifiedAt, new Date());

        studioFlierMapper.update(null, wrapper);
    }

    @Override
    public void removeOneFlier(String flierId) {



        // 删除 flier_copy 中的关联数据
        LambdaQueryWrapper<FlierCopy> copyWrapper = new LambdaQueryWrapper<>();
        copyWrapper.eq(FlierCopy::getFlierId, flierId);
        studioFlierCopyMapper.delete(copyWrapper);

        // 删除 flier_visitor 中的关联数据
        LambdaQueryWrapper<FlierVisitor> visitorWrapper = new LambdaQueryWrapper<>();
        visitorWrapper.eq(FlierVisitor::getFlierId, flierId);
        studioFlierVisitorMapper.delete(visitorWrapper);

        // 删除 flier
        studioFlierMapper.deleteById(flierId);


    }

    @Override
    public void changeFlierMaterial(String flierId, String newContentLink) {

        // 更新 contentLink
        LambdaUpdateWrapper<Flier> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Flier::getId, flierId)
                .set(Flier::getContentLink, newContentLink)
                .set(Flier::getModifiedAt, new Date());

        studioFlierMapper.update(null, wrapper);
    }

    @Override
    public void interactFlier(StudioInteractFlierDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        String flierId = dto.getFlierId();
        String event = dto.getEvent();

        // 1. 校验传单
        validateAndGetFlier(flierId);

        // 2. 按 receiver / visitor 分发
        boolean isLike = isLikeEvent(event);
        if (isReceiverEvent(event)) {
            doReceiverInteract(flierId, brandId, isLike);
        } else {
            doVisitorInteract(flierId, brandId, isLike);
        }
    }

    // ======================== 校验 ========================

    private Flier validateAndGetFlier(String flierId) {
        Flier flier = studioFlierMapper.selectById(flierId);
        if (flier == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (!FlierStatusEnum.NORMAL.getValue().equals(flier.getStatus())) {
            throw new QuickMessageException("传单状态不支持该操作");
        }
        return flier;
    }

    private boolean isLikeEvent(String event) {
        return FlierInteractEventEnum.RECEIVER_LIKE.getValue().equals(event)
                || FlierInteractEventEnum.VISITOR_LIKE.getValue().equals(event);
    }

    private boolean isReceiverEvent(String event) {
        return FlierInteractEventEnum.RECEIVER_LIKE.getValue().equals(event)
                || FlierInteractEventEnum.RECEIVER_CTA_CLICK.getValue().equals(event);
    }

    // ======================== receiver 交互 ========================

    private void doReceiverInteract(String flierId, String brandId, boolean isLike) {
        FlierCopy record = studioFlierCopyMapper.selectOne(
                new LambdaQueryWrapper<FlierCopy>()
                        .eq(FlierCopy::getFlierId, flierId)
                        .eq(FlierCopy::getReceiverBrandId, brandId));
        if (record == null) {
            throw new QuickMessageException("您未收到该传单");
        }
        if(isLike && SwitchCheckEnum.ENABLE.getMark().equals(record.getHasLike())){
            throw new QuickMessageException("请勿重复操作");
        }
        if(!isLike && SwitchCheckEnum.ENABLE.getMark().equals(record.getHasClickCta())){
            throw new QuickMessageException("请勿重复操作");
        }

        if (isLike) {
            refreshFlierLikes(flierId,()-> {
                record.setHasLike(SwitchCheckEnum.ENABLE.getMark());
                record.setModifiedAt(new Date());
                studioFlierCopyMapper.updateById(record);
            });
            baseElectricityService.topup(brandId, 1, "阅读传单奖励",ElectricityBusinessTypeEnum.READ_FLIER_BONUS.getMark(),
                    flierId, "目标传单："+flierId);
        } else {
            refreshFlierCtaClicks(flierId, ()->{
                record.setHasClickCta(SwitchCheckEnum.ENABLE.getMark());
                record.setModifiedAt(new Date());
                studioFlierCopyMapper.updateById(record);
            });
        }


    }

    // ======================== visitor 交互 ========================

    private void doVisitorInteract(String flierId, String brandId, boolean isLike) {
        FlierVisitor record = studioFlierVisitorMapper.selectOne(
                new LambdaQueryWrapper<FlierVisitor>()
                        .eq(FlierVisitor::getFlierId, flierId)
                        .eq(FlierVisitor::getVisitorBrandId, brandId));

        if (record == null) {
            FlierVisitor newRecord = new FlierVisitor()
                    .setId(IdUtil.simpleUUID())
                    .setFlierId(flierId)
                    .setVisitorBrandId(brandId)
                    .setHasLike(SwitchCheckEnum.CLOSE.getMark())
                    .setHasClickCta(SwitchCheckEnum.CLOSE.getMark())
                    .setCreatedAt(new Date())
                    .setModifiedAt(new Date());
            try {
                studioFlierVisitorMapper.insert(newRecord);
                record = newRecord;
            } catch (DuplicateKeyException e) {
                throw new QuickMessageException("请勿重复操作");
            }
        }

        // 锁定 effectively final 引用
        final FlierVisitor finalRecord = record;

        if (isLike) {
            refreshFlierLikes(flierId,()-> {
                finalRecord.setHasLike(SwitchCheckEnum.ENABLE.getMark());
                finalRecord.setModifiedAt(new Date());
                studioFlierVisitorMapper.updateById(finalRecord);
            });
        } else {
            refreshFlierCtaClicks(flierId, ()->{
                finalRecord.setHasClickCta(SwitchCheckEnum.ENABLE.getMark());
                finalRecord.setModifiedAt(new Date());
                studioFlierVisitorMapper.updateById(finalRecord);
            });
        }
    }

    private void refreshFlierLikes(String flierId,   Runnable fallback){
        LambdaUpdateWrapper<Flier> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Flier::getId, flierId)
                .setSql("likes = likes + 1")
                .set(Flier::getModifiedAt, new Date());
        studioFlierMapper.update(null, wrapper);

        fallback.run();
    }

    private void refreshFlierCtaClicks(String flierId, Runnable fallback){
        LambdaUpdateWrapper<Flier> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Flier::getId, flierId)
                .setSql("cta_clicks = cta_clicks + 1")
                .set(Flier::getModifiedAt, new Date());
        studioFlierMapper.update(null, wrapper);

        fallback.run();
    }


    @Override
    public Flier findFlierById(String flierId) {
        return studioFlierMapper.selectById(flierId);
    }

    @Override
    public FlierCopy findFlierCopyById(String flierCopyId) {
        return studioFlierCopyMapper.selectById(flierCopyId);
    }

    @Override
    public void removeOneFlierCopy(String flierCopyId) {
        // 删除 flier_copy
        studioFlierCopyMapper.deleteById(flierCopyId);
    }

    @Override
    public StudioFlierVisitorInfoVO findVisitorFlierInfo(StudioFlierVisitorInfoDTO dto) {
        StudioFlierVisitorInfoRO ro = studioFlierMapper.selectVisitorFlierInfo(dto.getFlierId(), dto.getVisitorBrandId());
        if (ro == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        StudioFlierVisitorInfoVO vo = new StudioFlierVisitorInfoVO();
        vo.setFlier(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void handoutFlier(StudioHandoutFlierDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // 校验传单存在
        Flier existing = studioFlierMapper.selectById(dto.getFlierId());
        if (existing == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // 只有作者才能分发
        if (!existing.getAuthorBrandId().equals(brandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // 状态必须为 NORMAL
        if (!FlierStatusEnum.NORMAL.getValue().equals(existing.getStatus())) {
            throw new QuickMessageException("当前状态不支持分发");
        }
        // 不能分发给自己
        if (brandId.equals(dto.getReceiverBrandId())) {
            throw new QuickMessageException("不能给自己分发传单");
        }
        Brand receiverBrand = studioBrandMapper.selectById(dto.getReceiverBrandId());
        if (receiverBrand == null) {
            throw new QuickMessageException("接收者数据未找到");
        }


        // 检查源能是否充足
        Brand brand = studioBrandMapper.selectById(brandId);
        if (brand.getElectricity() == null || brand.getElectricity() < 1) {
            throw new QuickMessageException("源能不足，无法分发传单");
        }

        // 扣除源能 2 点
        baseElectricityService.deduct(brandId, 2, "分发传单",ElectricityBusinessTypeEnum.DEDUCT_ELECTRICITY_FOR_HANDOUT_FLIER.getMark(),
                dto.getFlierId(), "目标传单："+dto.getFlierId());

        // 创建 flier_copy 记录
        FlierCopy flierCopy = new FlierCopy()
                .setId(IdUtil.simpleUUID())
                .setFlierId(dto.getFlierId())
                .setReceiverBrandId(dto.getReceiverBrandId())
                .setHasLike(SwitchCheckEnum.CLOSE.getMark())
                .setHasClickCta(SwitchCheckEnum.CLOSE.getMark())
                .setCreatedAt(new Date())
                .setModifiedAt(new Date());
        try {
            studioFlierCopyMapper.insert(flierCopy);
        } catch (DuplicateKeyException e) {
            throw new QuickMessageException("传单已分发给该接收者，请勿重复操作");
        }

        // 增加 copies 计数
        LambdaUpdateWrapper<Flier> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Flier::getId, dto.getFlierId())
                .setSql("copies = copies + 1")
                .set(Flier::getModifiedAt, new Date());
        studioFlierMapper.update(null, wrapper);
    }

    @Override
    public void blockedFlier(String flierId) {
        // 1. 校验操作权限：仅托管（HOSTING）角色可执行
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(
                new LambdaQueryWrapper<Bluvarrier>()
                        .eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark()));
        if (bluvarrier == null || !currentBrandId.equals(bluvarrier.getBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // 2. 校验传单存在
        Flier targetFlier = studioFlierMapper.selectById(flierId);
        if (targetFlier == null) {
            throw new QuickMessageException("传单不存在");
        }

        // 3. 清除 flier_copy 关联数据，阻止继续传播
        studioFlierCopyMapper.delete(
                new LambdaQueryWrapper<FlierCopy>()
                        .eq(FlierCopy::getFlierId, flierId));

        // 4. 清除 flier_visitor 关联数据，阻止继续交互
        studioFlierVisitorMapper.delete(
                new LambdaQueryWrapper<FlierVisitor>()
                        .eq(FlierVisitor::getFlierId, flierId));

        // 5. 标记传单为封禁状态
        Flier update = new Flier();
        update.setId(flierId)
                .setStatus(FlierStatusEnum.FREEZE.getValue())
                .setModifiedAt(new Date());
        studioFlierMapper.updateById(update);
    }

    @Override
    public StudioFetchReceiverFlierPageVO findReceiverFlierPage(StudioFetchReceiverFlierPageDTO dto) {
        Page<StudioFetchReceiverFlierPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        String receiverBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<StudioFetchReceiverFlierPageRO> flierPage = studioFlierMapper.selectReceiverFlierPage(page, dto, receiverBrandId);

        StudioFetchReceiverFlierPageVO vo = new StudioFetchReceiverFlierPageVO();
        vo.setFlier(flierPage);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
