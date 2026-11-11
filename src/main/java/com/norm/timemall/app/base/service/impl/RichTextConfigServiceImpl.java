package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.norm.timemall.app.base.mapper.RichTextConfigMapper;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.service.RichTextConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RichTextConfigServiceImpl implements RichTextConfigService {
    @Autowired
    private RichTextConfigMapper richTextConfigMapper;
    @Override
    public RichTextConfig getRichTextConfig(String contentType, String contentNo) {
        QueryWrapper<RichTextConfig> wrapper = Wrappers.query();
        wrapper.eq("content_type",contentType)
                .eq("content_no",contentNo);
        return richTextConfigMapper.selectOne(wrapper);
    }
}
