package com.lby.chatgpt.domain.chat;

import com.lby.chatgpt.domain.other.Usage;
import com.sun.prism.Texture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 对话请求结果信息
 * @author lby
 */
@Data
public class ChatCompletionResponse implements Serializable {

    /** ID */
    private String id;
    /** 对象 */
    private String object;
    /** 模型 */
    private String model;
    /** 对话 */
    private List<ChatChoice> choices;
    /** 创建 */
    private long created;
    /** 耗材 */
    private Usage usage;
}
