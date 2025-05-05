package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductChangeDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductChangeDeliverMaterialDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductCreateDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductStatusManagementDTO;
import com.norm.timemall.app.studio.domain.vo.FetchVirtualProductMetaInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioVirtualProductService {
    FetchVirtualProductMetaInfoVO findProductMetaInfo(String productId);

    void modifyProductStatus(StudioVirtualProductStatusManagementDTO dto);

    void removeOneProduct(String productId);

    String newProduct(StudioVirtualProductCreateDTO dto, String thumbnailUrl);

    void changeProductMetaInfo(StudioVirtualProductChangeDTO dto);

    VirtualProduct findOneProduct(String productId);

    void changeProductThumbnail(String productId, String thumbnailUrl);

    void modifyProductDeliverMaterial(StudioVirtualProductChangeDeliverMaterialDTO dto, String deliverAttachmentUrl);

}
