package com.example.flyservice.controller;


import com.example.flyservice.dto.request.TicketRequest;
import com.example.flyservice.service.TicketProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketProducer ticketProducer;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping
    public String createTicket(@RequestBody TicketRequest request) {
        ticketProducer.sendTicketRequest(request);
        return "Ticket request sent for user: " + request.getUserId();
    }

    @GetMapping("/available")
    public String getAvailableTickets() {
        Object available = redisTemplate.opsForValue().get("tickets:available");
        return "Available tickets: " + (available != null ? available : "0");
    }

    @GetMapping("/{id}/status")
    public String getTicketStatus(@PathVariable String id) {
        Object status = redisTemplate.opsForValue().get("tickets:status:" + id);
        return "Ticket status: " + (status != null ? status : "Not Found");
    }
}
