package com.devwue.spring.api.external.papago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TranslateResponseDto {
    @JsonProperty("message")
    private Message message;
}
