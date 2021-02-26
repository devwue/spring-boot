package com.devwue.spring.api.service;

import com.devwue.spring.api.client.PapagoClient;
import com.devwue.spring.api.external.papago.DetectLangResponseDto;
import com.devwue.spring.api.external.papago.Message;
import com.devwue.spring.api.external.papago.TranslateResponseDto;
import com.devwue.spring.api.external.papago.TranslateResult;
import com.devwue.spring.api.external.papago.TranslateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("local")
@SpringBootTest
public class PapagoServiceTests {
    @Autowired
    PapagoClient papagoClient;

    @Autowired
    PapagoService papagoService;

    @Test
    @DisplayName("파파고 한글 번역 테스트")
    public void translate() {
        String text = "만나서 반갑습니다.";
        HashMap<String, String> header = papagoService.buildHeader();

        TranslateRequestDto.TranslateRequestDtoBuilder papagoTranslateDtoBuilder = TranslateRequestDto.builder().source("ko").target("en").text(text);
        TranslateResponseDto response = papagoClient.translateWithMap(papagoTranslateDtoBuilder.build(), header);
        System.out.println(response.getMessage().getResult().getTranslatedText());

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isInstanceOf(Message.class);
        assertThat(response.getMessage().getResult()).isInstanceOf(TranslateResult.class);
        assertThat(response.getMessage().getResult().getTranslatedText()).contains("Nice to meet you");
    }

    @DisplayName("파파고 언어 감지 테스트")
    @ParameterizedTest
    @CsvSource(value = {"만나서 반갑습니다.:ko", "Hello world:en"}, delimiter = ':')
    public void detectLang(String text, String expected) {
        HashMap<String, String> body = new HashMap<>();
        body.put("query", text);
        DetectLangResponseDto detectLangResponseDto = papagoClient.detectLang(body, papagoService.buildHeader());

        System.out.println(detectLangResponseDto);
        assertThat(detectLangResponseDto).isNotNull();
        assertThat(detectLangResponseDto.getLangCode()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"만나서 반갑습니다.:Nice to meet you"}, delimiter = ':')
    public void service(String text, String expected) {
        String response = papagoService.translate(text);
        assertThat(response).contains(expected);
    }
}
