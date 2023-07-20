package com.norm.timemall.app.pay.service;

import org.springframework.stereotype.Service;

@Service
public interface DefaultPayService {
    String transfer(String param);
    void refund(String param);
}
