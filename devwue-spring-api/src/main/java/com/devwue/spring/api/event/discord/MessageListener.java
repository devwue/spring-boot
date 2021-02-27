package com.devwue.spring.api.event.discord;

import com.devwue.spring.api.service.TranslateService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
public abstract class MessageListener {
    private final TranslateService translateService;

    protected MessageListener(TranslateService translateService) {
        this.translateService = translateService;
    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> Mono.zip(
                        Mono.justOrEmpty(translate(message.getContent())),
                        Mono.justOrEmpty(message.getChannel())))
                .flatMap(tuple -> {
                    String translatedMessage = tuple.getT1();
                    MessageChannel channel = tuple.getT2().block();

                    log.error("final: {}", translatedMessage);
                    if (StringUtils.isEmpty(translatedMessage)) {
                        translatedMessage = eventMessage.getContent();
                    }

                    return channel.createMessage(translatedMessage);
                })
                .then();
    }

    public String translate(String text) {
        HashMap<String, String> translate = (HashMap<String, String>) translateService.translate(text);
        log.error("trans ({}) - {}", translate.get("text"));
        return translate.get("text");
    }
}
