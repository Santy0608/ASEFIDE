package com.backend_app.backend_app.chatbot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "claude.api")
public class ClaudeProperties {

    private String key;
    private String url;
    private String model;
    private Integer maxTokens;

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public Integer getMaxTokens(){
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens){
        this.maxTokens = maxTokens;
    }

}
