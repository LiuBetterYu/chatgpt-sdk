package com.lby.chatgpt.domain.images;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图片配置枚举
 * @author lby
 */
public class ImageEnum {

    @Getter
    @AllArgsConstructor
    public enum Size {
        size_256("256x256"),
        size_512("512x512"),
        size_1024("1024x1024"),
        ;
        private String code;
    }

    @Getter
    @AllArgsConstructor
    public enum ResponseFormat {
        URL("url"),
        B64_JSON("b64_json"),
        ;
        private String code;
    }
}
