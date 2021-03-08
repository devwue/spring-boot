package com.devwue.spring.api.event.devwue;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPoint {
    private Long userId;
    private String pointType;
    private Integer point;
}
