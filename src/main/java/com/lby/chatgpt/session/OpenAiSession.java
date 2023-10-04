package com.lby.chatgpt.session;

import com.lby.chatgpt.domain.chat.ChatCompletionRequest;
import com.lby.chatgpt.domain.chat.ChatCompletionResponse;
import com.lby.chatgpt.domain.qa.QACompletionRequest;
import com.lby.chatgpt.domain.qa.QACompletionResponse;

/**
 * OpenAi 会话接口
 * @author lby
 */
public interface OpenAiSession {

    /**
     * 文本问答
     * @param qaCompletionRequest  请求信息
     * @return                     返回结果
     */
    QACompletionResponse completion(QACompletionRequest qaCompletionRequest);

    /**
     * 文本问答；简单请求
     * @param question 请求信息
     * @return         返回结果
     */
    QACompletionResponse completions(String question);

    /**
     * 默认 GPT-3.5 问答模型
     * @param chatCompletionRequest 请求信息
     * @return                      返回结果
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);

}
