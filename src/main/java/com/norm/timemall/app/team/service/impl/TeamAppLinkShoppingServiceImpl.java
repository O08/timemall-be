package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.AppLinkShopping;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingCreateProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingEditProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import com.norm.timemall.app.team.mapper.TeamAppLinkShoppingMapper;
import com.norm.timemall.app.team.service.TeamAppLinkShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamAppLinkShoppingServiceImpl implements TeamAppLinkShoppingService {
    @Autowired
    private TeamAppLinkShoppingMapper teamAppLinkShoppingMapper;

    @Override
    public IPage<TeamAppLinkShoppingFetchFeedPageRO> findFeeds(TeamAppLinkShoppingFetchFeedPageDTO dto) {

        IPage<TeamAppLinkShoppingFetchFeedPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppLinkShoppingMapper.selectPageByQ(page,dto);

    }

    @Override
    public void newFeed(TeamAppLinkShoppingCreateProductDTO dto, String coverUrl) {

        AppLinkShopping product = new AppLinkShopping();
        product.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setLinkUrl(dto.getLinkUrl())
                .setPrice(dto.getPrice())
                .setCoverUrl(coverUrl)
                .setViews(0)
                .setOasisChannelId(dto.getChannel())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamAppLinkShoppingMapper.insert(product);

    }

    @Override
    public void changeProduct(TeamAppLinkShoppingEditProductDTO dto) {

        AppLinkShopping product = new AppLinkShopping();
        product.setId(dto.getId())
                .setTitle(dto.getTitle())
                .setPrice(dto.getPrice())
                .setModifiedAt(new Date());

        LambdaQueryWrapper<AppLinkShopping> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppLinkShopping::getId,dto.getId());

        teamAppLinkShoppingMapper.update(product, wrapper);

    }

    @Override
    public AppLinkShopping findOneFeed(String id) {
        return teamAppLinkShoppingMapper.selectById(id);
    }

    @Override
    public void doRemoveProduct(String id) {
        teamAppLinkShoppingMapper.deleteById(id);
    }

    @Override
    public void storeProductStatisticsData(String id) {
        teamAppLinkShoppingMapper.autoIncrementViewsById(id);
    }

    @Override
    public void removeChannelData(String channel) {

        LambdaQueryWrapper<AppLinkShopping> productWrapper= Wrappers.lambdaQuery();
        productWrapper.eq(AppLinkShopping::getOasisChannelId,channel);
        teamAppLinkShoppingMapper.delete(productWrapper);

    }
}
