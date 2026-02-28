package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.TransactionsQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskTransactionsQueryMapper extends BaseMapper<TransactionsQuery> {
    void reloadTransQueryTb();
}
