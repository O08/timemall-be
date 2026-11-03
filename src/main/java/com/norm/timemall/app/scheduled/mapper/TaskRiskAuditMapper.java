package com.norm.timemall.app.scheduled.mapper;

import com.norm.timemall.app.base.mo.RiskAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 风险审计表(risk_audit)数据Mapper
 *
 * @author kancy
 * @since 2024-06-10 10:58:24
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskRiskAuditMapper extends BaseMapper<RiskAudit> {

    void refreshRiskAudit();

}
