package com.lby.chatgpt.domain.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 对话信息
 * @author lby
 */
@Data
public class ChatChoice implements Serializable {

    private long index;

    @JsonProperty("delta")
    private Message delta;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;
}
