package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.FetchRcPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallGetRcListRO;
import org.springframework.stereotype.Service;

@Service
public interface RcLibraryService {
    void newRc(String rcAddress, String title, String previewAddress,String thumbnailAddress,String tags);

    IPage<MallGetRcListRO> findRcList(FetchRcPageDTO q);
}
