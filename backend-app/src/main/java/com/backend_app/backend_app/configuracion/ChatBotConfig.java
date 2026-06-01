package com.backend_app.backend_app.configuracion;

import com.backend_app.backend_app.chatbot.ClaudeProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(ClaudeProperties.class)  // ← activa el binding aquí
public class ChatBotConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(); // ← agregar esto
    }

    @Bean
    public WebClient claudeWebClient(ClaudeProperties props) {
        return WebClient.builder()
                .baseUrl(props.getUrl())
                .defaultHeader("x-api-key", props.getKey())
                .defaultHeader("anthropic-version", "2023-06-01")
                .defaultHeader("content-type", "application/json")
                .codecs(configurer ->
                        configurer.defaultCodecs()
                                .maxInMemorySize(2 * 1024 * 1024) // 2MB
                )
                .build();
    }

}
