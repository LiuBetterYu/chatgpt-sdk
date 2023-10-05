package com.lby.chatgpt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lby.chatgpt.common.Constants;
import com.lby.chatgpt.domain.billing.BillingUsage;
import com.lby.chatgpt.domain.billing.Subscription;
import com.lby.chatgpt.domain.chat.ChatCompletionRequest;
import com.lby.chatgpt.domain.chat.ChatCompletionResponse;
import com.lby.chatgpt.domain.chat.Message;
import com.lby.chatgpt.domain.edits.EditRequest;
import com.lby.chatgpt.domain.edits.EditResponse;
import com.lby.chatgpt.domain.embedd.EmbeddingResponse;
import com.lby.chatgpt.domain.files.DeleteFileResponse;
import com.lby.chatgpt.domain.files.UploadFileResponse;
import com.lby.chatgpt.domain.images.ImageEnum;
import com.lby.chatgpt.domain.images.ImageRequest;
import com.lby.chatgpt.domain.images.ImageResponse;
import com.lby.chatgpt.domain.other.OpenAiResponse;
import com.lby.chatgpt.domain.qa.QACompletionRequest;
import com.lby.chatgpt.domain.qa.QACompletionResponse;
import com.lby.chatgpt.session.Configuration;
import com.lby.chatgpt.session.OpenAiSession;
import com.lby.chatgpt.session.OpenAiSessionFactory;
import com.lby.chatgpt.session.defaults.DefaultOpenAiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * @author lby
 */
@Slf4j
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com/");         // https://pro-share-aws-api.zcyai.com/authorize?username=xfg&password=123
        configuration.setApiKey("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");               // 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
        // 测试时候，需要先获得授权token：http://api.xfg.im:8080/authorize?username=xfg&password=123 - 此地址暂时有效，后续根据课程首页说明获取token；https://t.zsxq.com/0d3o5FKvc
        configuration.setAuthToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZmciLCJleHAiOjE2ODMyODE2NzEsImlhdCI6MTY4MzI3ODA3MSwianRpIjoiMWUzZTkwYjYtY2UyNy00NzNlLTk5ZTYtYWQzMWU1MGVkNWE4IiwidXNlcm5hbWUiOiJ4ZmcifQ.YgQRJ2U5-9uydtd6Wbkg2YatsoX-y8mS_OJ3FdNRaX0");
        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. 开启会话
        this.openAiSession = factory.openSession();
    }

    /**
     * 简单问答模式
     */
    @Test
    public void test_qa_completions() throws JsonProcessingException {
        QACompletionResponse response01 = openAiSession.completions("java的数据类型有哪些？");
        log.info("测试结果：{}", new ObjectMapper().writeValueAsString(response01.getChoices()));
    }

    /**
     * 此对话模型 3.5 接近于官网体验
     */
    @Test
    public void test_chat_completions() {
        // 1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        // 2. 发起请求
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);
        // 3. 解析结果
        chatCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
        });
    }

    /**
     * 简单问答模式 * 流式应答
     */
    @Test
    public void test_qa_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. 创建参数
        QACompletionRequest request = QACompletionRequest
                .builder()
                .prompt("写个java冒泡排序")
                .stream(true)
                .build();

        for (int i = 0; i < 1; i++) {
            // 2. 发起请求
            EventSource eventSource = openAiSession.completions(request, new EventSourceListener() {
                @Override
                public void onEvent(EventSource eventSource, String id, String type, String data) {
                    log.info("测试结果：{}", data);
                }
            });
        }

        // 等待
        new CountDownLatch(1).await();
    }

    /**
     * 此对话模型 3.5 接近于官网体验 & 流式应答
     */
    @Test
    public void test_chat_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .stream(true)
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        // 2. 发起请求
        EventSource eventSource = openAiSession.chatCompletions(chatCompletion, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                log.info("测试结果：{}", data);
            }
        });
        // 等待
        new CountDownLatch(1).await();
    }

    /**
     * 文本修复
     */
    @Test
    public void test_edit() {
        // 文本请求
        EditRequest textRequest = EditRequest.builder()
                .input("码农会锁")
                .instruction("帮我修改错字")
                .model(EditRequest.Model.TEXT_DAVINCI_EDIT_001.getCode()).build();
        EditResponse textResponse = openAiSession.edit(textRequest);
        log.info("测试结果：{}", textResponse);

        // 代码请求
        EditRequest codeRequest = EditRequest.builder()
                // j <= 10 应该修改为 i <= 10
                .input("for (int i = 1; j <= 10; i++) {\n" +
                        "    System.out.println(i);\n" +
                        "}")
                .instruction("这段代码执行时报错，请帮我修改").model(EditRequest.Model.CODE_DAVINCI_EDIT_001.getCode()).build();
        EditResponse codeResponse = openAiSession.edit(codeRequest);
        log.info("测试结果：{}", codeResponse);
    }

    /**
     * 生成图片
     */
    @Test
    public void test_genImages() {
        // 方式1，简单调用
        ImageResponse imageResponse01 = openAiSession.genImages("画一个996加班的程序员");
        log.info("测试结果：{}", imageResponse01);

        // 方式2，调参调用
        ImageResponse imageResponse02 = openAiSession.genImages(ImageRequest.builder()
                .prompt("画一个996加班的程序员")
                .size(ImageEnum.Size.size_256.getCode())
                .responseFormat(ImageEnum.ResponseFormat.B64_JSON.getCode()).build());
        log.info("测试结果：{}", imageResponse02);
    }

    /**
     * 修改图片，有3个方法，入参不同。
     */
    @Test
    public void test_editImages() throws IOException {
        ImageResponse imageResponse = openAiSession.editImages(new File("/Users/fuzhengwei/1024/KnowledgePlanet/chatgpt/chatgpt-sdk-java/docs/images/996.png"), "去除图片中的文字");
        log.info("测试结果：{}", imageResponse);
    }

    @Test
    public void test_embeddings() {
        EmbeddingResponse embeddingResponse = openAiSession.embeddings("哈喽", "嗨", "hi!");
        log.info("测试结果：{}", embeddingResponse);
    }

    @Test
    public void test_files() {
        OpenAiResponse<File> openAiResponse = openAiSession.files();
        log.info("测试结果：{}", openAiResponse);
    }

    @Test
    public void test_uploadFile() {
        UploadFileResponse uploadFileResponse = openAiSession.uploadFile(new File("/Users/fuzhengwei/1024/KnowledgePlanet/chatgpt/chatgpt-sdk-java/docs/files/introduce.md"));
        log.info("测试结果：{}", uploadFileResponse);
    }

    @Test
    public void test_deleteFile() {
        DeleteFileResponse deleteFileResponse = openAiSession.deleteFile("file id 上传后才能获得");
        log.info("测试结果：{}", deleteFileResponse);
    }

    @Test
    public void test_subscription() {
        Subscription subscription = openAiSession.subscription();
        log.info("测试结果：{}", subscription);
    }

    @Test
    public void test_billingUsage() {
        BillingUsage billingUsage = openAiSession.billingUsage(LocalDate.of(2023, 3, 20), LocalDate.now());
        log.info("测试结果：{}", billingUsage.getTotalUsage());
    }
}
