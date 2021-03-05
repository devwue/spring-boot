package com.devwue.spring.api.service;

import com.devwue.spring.api.event.devwue.KafkaEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void produceMessage(String topic, Object payload) {
        ListenableFuture listenableFuture = kafkaTemplate.send(topic, payload);
        // handler
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("error: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult result) {
                log.debug("offset: {}", result.getRecordMetadata().offset());
            }
        });
    }

    @KafkaListener(topics = "test")
    public void consumerMessage(KafkaEvent event, Acknowledgment acknowledgment) {
        log.debug("kafka message consume: {}", event.getEmail());
        acknowledgment.acknowledge();
    }

    // 다른 방법
    @KafkaListener(topics = "test", groupId = "test2")
    public void consumerMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        log.debug("topic:{}, headers: {}, offset: {}, value: {} ", record.topic(), record.headers().toArray(), record.offset(), record.value());
        try {

            acknowledgment.acknowledge();
        } catch (Exception e) {
            // seek to?
        }
    }
}
