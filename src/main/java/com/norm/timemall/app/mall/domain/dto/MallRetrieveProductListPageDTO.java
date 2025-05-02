package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallRetrieveProductListPageDTO extends PageDTO {
    // search keyword
    private String q;
    // 预算
    @Positive(message = "budget max value should be positive.")
    private BigDecimal budgetMin;
    @Positive(message = "budget max value should be positive.")
    private BigDecimal budgetMax;

    // 排序方式
    private String sort;

    private String location;
    private boolean online;

}
