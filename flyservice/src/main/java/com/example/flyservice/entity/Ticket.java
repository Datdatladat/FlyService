package com.example.flyservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    private String id;
    private String passengerName;
}
