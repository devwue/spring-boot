package com.devwue.spring.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostTypeResponse {
    private String type;
    private Integer count;
}
