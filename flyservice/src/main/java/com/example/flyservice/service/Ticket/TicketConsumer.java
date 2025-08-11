package com.example.flyservice.kafka;

import com.example.flyservice.dto.request.TicketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketConsumer {

    private final RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "ticket-requests", groupId = "ticket-service")
    public void consume(TicketRequest request) {
        String availableKey = "tickets:available";
        String statusKey = "tickets:status:" + UUID.randomUUID();

        Long remaining = redisTemplate.opsForValue().decrement(availableKey, request.getQuantity());

        if (remaining != null && remaining >= 0) {
            redisTemplate.opsForValue().set(statusKey, "CONFIRMED");
        } else {
            redisTemplate.opsForValue().increment(availableKey, request.getQuantity()); // rollback
            redisTemplate.opsForValue().set(statusKey, "FAILED - Not enough tickets");
        }
    }
}
