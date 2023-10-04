package com.lby.chatgpt.domain.files;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除文件应答
 * @author lby
 */
@Data
public class DeleteFileResponse implements Serializable {

    /** 文件ID */
    private String id;
    /** 对象；file */
    private String object;
    /** 删除；true */
    private boolean deleted;
}
