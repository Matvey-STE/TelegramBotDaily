package com.matveyvs1987.configuration;

import com.matveyvs1987.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NodeConfiguration {
    @Value("${salt}")
    private String salt;
    @Bean
    public CryptoTool getCryptoTool(){
        return new CryptoTool(salt);
    }
}
