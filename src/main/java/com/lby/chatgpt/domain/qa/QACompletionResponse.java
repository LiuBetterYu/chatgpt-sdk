package com.lby.chatgpt.domain.qa;

import com.lby.chatgpt.domain.other.Choice;
import com.lby.chatgpt.domain.other.Usage;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Serializable;

/**
 * @author lby
 */
@Data
public class QACompletionResponse implements Serializable {

    /** ID */
    private String id;
    /** 对象 */
    private String object;
    /** 模型 */
    private String model;
    /** 对话 */
    private Choice[] choices;
    /** 创建 */
    private long created;
    /** 耗材 */
    private Usage usage;

    private String warning;
}
