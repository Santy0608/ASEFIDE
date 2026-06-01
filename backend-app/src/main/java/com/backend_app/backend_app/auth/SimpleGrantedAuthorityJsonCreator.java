package com.backend_app.backend_app.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonIgnore
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){}

}
