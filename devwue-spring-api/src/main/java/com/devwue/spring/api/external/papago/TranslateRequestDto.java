package com.devwue.spring.api.external.papago;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TranslateRequestDto {
    private String source;
    private String target;
    private String text;
}
