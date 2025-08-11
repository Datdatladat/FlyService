package com.example.flyservice.repository;

import com.example.flyservice.entity.Ticket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketRepository {

    public static final String HASH_KEY = "Ticket";
    private final RedisTemplate<String, Object> redisTemplate;

    public TicketRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Ticket save(Ticket ticket) {
        redisTemplate.opsForHash().put(HASH_KEY, ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY)
                .stream()
                .map(obj -> (Ticket) obj)
                .collect(Collectors.toList());
    }

    public Ticket findById(String id) {
        return (Ticket) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteById(String id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "Ticket Removed Successfully";
    }
}
