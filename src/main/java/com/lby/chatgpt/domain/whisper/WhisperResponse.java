package com.lby.chatgpt.domain.whisper;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lby
 */
@Data
public class WhisperResponse implements Serializable {

    private String text;
}
