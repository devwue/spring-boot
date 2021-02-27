package com.devwue.spring.api.event.discord;

import com.devwue.spring.api.service.TranslateService;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import reactor.core.publisher.Mono;

public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {
    public MessageUpdateListener(TranslateService translateService) {
        super(translateService);
    }

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processCommand);
    }
}
