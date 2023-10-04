package com.lby.chatgpt.domain.billing;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 消耗列表数据
 * @author lby
 */
@Data
public class LineItem {
    /** 模型 */
    private String name;
    /** 金额 */
    private BigDecimal cost;
}
