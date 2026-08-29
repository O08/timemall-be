package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.base.mo.VirtualProductRandomItem;
import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomChangeItemDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomCreateItemDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVrProductRandomItemListRO;
import com.norm.timemall.app.studio.mapper.StudioVirtualProductMapper;
import com.norm.timemall.app.studio.mapper.StudioVirtualProductRandomItemMapper;
import com.norm.timemall.app.studio.service.StudioVirtualProductRandomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StudioVirtualProductRandomItemServiceImpl implements StudioVirtualProductRandomItemService {
    @Autowired
    private StudioVirtualProductRandomItemMapper studioVirtualProductRandomItemMapper;
    @Autowired
    private StudioVirtualProductMapper studioVirtualProductMapper;



    @Override
    public ArrayList<StudioFetchVrProductRandomItemListRO> findMerchandise(String productId) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        return studioVirtualProductRandomItemMapper.selectMerchandiseByProductId(productId,sellerBrandId);
    }

    @Override
    public void newMerchandise(StudioVrProductRandomCreateItemDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        VirtualProduct product = studioVirtualProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("非法访问");
        }
        LambdaQueryWrapper<VirtualProductRandomItem> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(VirtualProductRandomItem::getProductId,dto.getProductId());
        Long rows = studioVirtualProductRandomItemMapper.selectCount(wrapper);
        if(rows>=100){
            throw new QuickMessageException("已达最大容量");
        }


        VirtualProductRandomItem shipping=new VirtualProductRandomItem();
        shipping.setId(IdUtil.simpleUUID())
                .setProductId(dto.getProductId())
                .setPack(dto.getPack())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioVirtualProductRandomItemMapper.insert(shipping);

    }

    @Override
    public void modifyMerchandise(StudioVrProductRandomChangeItemDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        VirtualProductRandomItem merchandise = studioVirtualProductRandomItemMapper.selectById(dto.getMerchandiseId());
        if(merchandise==null){
            throw new QuickMessageException("未找到相关货品数据");
        }
        VirtualProduct product = studioVirtualProductMapper.selectById(merchandise.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("非法访问");
        }
        merchandise.setPack(dto.getPack());
        merchandise.setModifiedAt(new Date());

        studioVirtualProductRandomItemMapper.updateById(merchandise);
    }

    @Override
    public void removeMerchandise(String id) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        VirtualProductRandomItem merchandise = studioVirtualProductRandomItemMapper.selectById(id);
        if(merchandise==null){
            throw new QuickMessageException("未找到相关货品数据");
        }
        VirtualProduct product = studioVirtualProductMapper.selectById(merchandise.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("非法访问");
        }

        studioVirtualProductRandomItemMapper.deleteById(id);

    }
}
