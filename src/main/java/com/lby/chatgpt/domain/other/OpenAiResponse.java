package com.lby.chatgpt.domain.other;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lby
 */
@Data
public class OpenAiResponse<T> implements Serializable {

    private String object;
    private List<T> data;

    @Data
    public class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }
}