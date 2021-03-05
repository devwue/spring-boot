package com.devwue.spring.api.event.devwue;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent {
    Long userId;
    String email;
    String createdAt;
}
