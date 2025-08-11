package com.example.flyservice.service;

import com.example.flyservice.dto.request.TicketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketProducer {
    private final KafkaTemplate<String, TicketRequest> kafkaTemplate;

    public void sendTicketRequest(TicketRequest request) {
        kafkaTemplate.send("ticket-requests", request);
    }
}
