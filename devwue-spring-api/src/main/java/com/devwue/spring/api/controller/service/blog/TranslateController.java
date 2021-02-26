package com.devwue.spring.api.controller.service.blog;

import com.devwue.spring.api.service.TranslateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/translate")
@RestController
public class TranslateController {
    private final TranslateService translateService;

    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }


    @GetMapping("")
    public Object index(@RequestParam("text") String query) {
        return translateService.translate(query);
    }
}
