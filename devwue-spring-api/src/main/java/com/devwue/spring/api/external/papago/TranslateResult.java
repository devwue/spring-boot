package com.devwue.spring.api.external.papago;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TranslateResult {
    String engineType;
    String pivot;
    String srcLangType;
    String tarLangType;
    String translatedText;
}
