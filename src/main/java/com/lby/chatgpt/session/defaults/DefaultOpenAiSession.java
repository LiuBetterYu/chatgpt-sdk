package com.lby.chatgpt.session.defaults;

import com.lby.chatgpt.IOpenAiApi;
import com.lby.chatgpt.domain.chat.ChatCompletionRequest;
import com.lby.chatgpt.domain.chat.ChatCompletionResponse;
import com.lby.chatgpt.domain.qa.QACompletionRequest;
import com.lby.chatgpt.domain.qa.QACompletionResponse;
import com.lby.chatgpt.session.OpenAiSession;
import io.reactivex.Single;
import lombok.var;

/**
 * @author lby
 */
public class DefaultOpenAiSession implements OpenAiSession {

    private IOpenAiApi openAiApi;

    public DefaultOpenAiSession(IOpenAiApi openAiApi) {
        this.openAiApi = openAiApi;
    }

    @Override
    public QACompletionResponse completion(QACompletionRequest qaCompletionRequest) {
        return this.openAiApi.completions(qaCompletionRequest).blockingGet();
    }

    @Override
    public QACompletionResponse completions(String question) {
        QACompletionRequest request = QACompletionRequest
                .builder()
                .prompt(question)
                .build();
        Single<QACompletionResponse> completions = this.openAiApi.completions(request);
        return completions.blockingGet();

    }

    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest) {
        return this.openAiApi.completions(chatCompletionRequest).blockingGet();
    }
}
