package com.java.cafenow.config;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.modelmapper.ModelMapper;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final String API_KEY;
    private final String API_SECRET;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Message message() {
        return new Message(API_KEY, API_SECRET);
    }
}