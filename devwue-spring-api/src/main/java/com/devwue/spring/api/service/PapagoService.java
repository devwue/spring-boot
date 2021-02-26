package com.devwue.spring.api.service;

import com.devwue.spring.api.client.PapagoClient;
import com.devwue.spring.api.external.papago.DetectLangResponseDto;
import com.devwue.spring.api.external.papago.TranslateResponseDto;
import com.devwue.spring.api.external.papago.TranslateRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PapagoService {
    private final PapagoClient papagoClient;

    @Value("${external.api.papago.clientId}")
    private String clientId;

    @Value("${external.api.papago.clientSecret}")
    private String clientSecret;

    public PapagoService(PapagoClient papagoClient) {
        this.papagoClient = papagoClient;
    }

    public String translate(String text) {
        TranslateRequestDto.TranslateRequestDtoBuilder papagoTranslateDtoBuilder = TranslateRequestDto.builder()
                .source(detect(text)).target("en").text(text);
        TranslateResponseDto response =  papagoClient.translateWithMap(papagoTranslateDtoBuilder.build(), buildHeader());

        return response.getMessage().getResult().getTranslatedText();
    }

    public String detect(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put("query", text);
        DetectLangResponseDto detectLangResponseDto = papagoClient.detectLang(map, buildHeader());

        return detectLangResponseDto.getLangCode();
    }

    public HashMap buildHeader() {
        HashMap<String, String> header = new HashMap<>();
        header.put("X-Naver-Client-Id", clientId);
        header.put("X-Naver-Client-Secret", clientSecret);
        return header;
    }
}
