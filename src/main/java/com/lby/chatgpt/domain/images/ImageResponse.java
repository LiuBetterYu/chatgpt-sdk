package com.lby.chatgpt.domain.images;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lby
 */
@Data
public class ImageResponse implements Serializable {

    /** 条目数据 */
    private List<Item> data;
    /** 创建时间 */
    private long created;
}
