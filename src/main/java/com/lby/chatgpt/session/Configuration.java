package com.lby.chatgpt.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author lby
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Configuration {

    @NotNull
    private String apiKey;

    private String apiHost;
    @NotNull
    private String authToken;
}
