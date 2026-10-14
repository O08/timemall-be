package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomChangeItemDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomCreateItemDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVrProductRandomItemListRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudioVirtualProductRandomItemService {
    ArrayList<StudioFetchVrProductRandomItemListRO> findMerchandise(String productId);

    void newMerchandise(StudioVrProductRandomCreateItemDTO dto);

    void modifyMerchandise(StudioVrProductRandomChangeItemDTO dto);

    void removeMerchandise(String id);
}
