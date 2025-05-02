package com.norm.timemall.app.base.service;

import org.springframework.stereotype.Service;

@Service
public interface DelAccountService {

    void delAlipayInfo();

    void delContactInfo();

    void delPrivateMsg();

    void delPrivateRel();

    void delFanMsg();

    void delGroupRel();

    void delOasisJoin();

    void delOasisMember();

    void labelOasisMarkAsChaos();

    void labelBrandAsClosed();

    void labelCellAsOffline();

    void labelCommercialPaperAsClosed();


    void labelVirtualProductAsOffline();
}
