package com.devwue.spring.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    private String name;
    private String contents;
}
