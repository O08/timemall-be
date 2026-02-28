package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.pojo.StudioFetchFirstSupplier;
import org.springframework.stereotype.Service;

@Service
public interface StudioFirstSupplierService {
    StudioFetchFirstSupplier findFirstSupplier(String q);


}
