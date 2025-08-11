package com.example.flyservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class FlyserviceApplication {

    private final RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(FlyserviceApplication.class, args);
    }

    @PostConstruct
    public void initTickets() {
        redisTemplate.opsForValue().set("tickets:available", "100");
    }
}
