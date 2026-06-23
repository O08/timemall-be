package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskTransactionsQueryMapper;
import com.norm.timemall.app.scheduled.service.TransQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransQueryServiceImpl implements TransQueryService {

    @Autowired
    private TaskTransactionsQueryMapper taskTransactionsQueryMapper;
    @Override
    public void loadTransData() {
        taskTransactionsQueryMapper.reloadTransQueryTb();
    }
}
