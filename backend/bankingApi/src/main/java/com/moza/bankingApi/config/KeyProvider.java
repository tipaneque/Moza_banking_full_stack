package com.moza.bankingApi.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KeyProvider {
    @Value("${app.security.secret}")
    private String secret;
}
