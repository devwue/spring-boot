package com.devwue.spring.api.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TranslateService {
    private final PapagoService papagoService;

    public TranslateService(PapagoService papagoService) {
        this.papagoService = papagoService;
    }

    public Object translate(String text) {
        HashMap<String, String> result = new HashMap<>();
        result.put("text", papagoService.translate(text));
        return result;
    }
}
