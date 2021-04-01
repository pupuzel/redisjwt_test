package com.jockjock.token.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestConfig {

    @Bean
    public ObjectMapper objectMapper () {
        return new ObjectMapper();
    }


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter (ObjectMapper objectMapper) {
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setPrettyPrint(true);

        return converter;
    }
}
