package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.RcLibraryTypeEnum;
import com.norm.timemall.app.base.mo.RcLibrary;
import com.norm.timemall.app.mall.domain.dto.FetchRcPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallGetRcListRO;
import com.norm.timemall.app.mall.mapper.MallRcLibraryMapper;
import com.norm.timemall.app.mall.service.RcLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RcLibraryServiceImpl implements RcLibraryService {
    @Autowired
    private MallRcLibraryMapper mallRcLibraryMapper;


    @Override
    public void newRc(String rcAddress, String title, String previewAddress,String thumbnailAddress) {

        RcLibrary rc= new RcLibrary();
        rc.setId(IdUtil.simpleUUID());
        rc.setRcType(RcLibraryTypeEnum.PPT.getMark())
                .setThumbnail(thumbnailAddress)
                .setDownloadUri(rcAddress)
                .setFileUri(rcAddress)
                .setTitle(title)
                .setPreviewUri(previewAddress).setCreateAt(new Date())
           .setModifiedAt(new Date());

        mallRcLibraryMapper.insert(rc);

    }

    @Override
    public IPage<MallGetRcListRO> findRcList(FetchRcPageDTO dto) {

        Page<MallGetRcListRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return         mallRcLibraryMapper.selectRcPage(page, dto);

    }
}
