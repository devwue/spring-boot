package com.devwue.spring.api.client;

import com.devwue.spring.api.config.FeignConfiguration;
import com.devwue.spring.api.external.papago.DetectLangResponseDto;
import com.devwue.spring.api.external.papago.TranslateResponseDto;
import com.devwue.spring.api.external.papago.TranslateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@FeignClient(name = "translatePapago", url = "${external.api.papago.url}", configuration = {FeignConfiguration.class})
public interface PapagoClient {
    @PostMapping("/v1/papago/n2mt")
    TranslateResponseDto translateWithMap(TranslateRequestDto translateRequestDto, @RequestHeader HashMap<String, String> headers);

    @PostMapping("/v1/papago/detectLangs")
    DetectLangResponseDto detectLang(@RequestBody HashMap<String, String> body, @RequestHeader HashMap<String, String> headers);
}
