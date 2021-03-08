package com.devwue.spring.api.service;

import com.devwue.spring.api.event.devwue.KafkaEvent;
import com.devwue.spring.avro.MemberPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;

@Slf4j
@Service
public class KafkaService implements ConsumerSeekAware {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private PointService pointService;

    private  static ThreadLocal<ConsumerSeekCallback> seekCallback = new ThreadLocal<>();

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
    @KafkaListener(topics = "point", groupId = "test2")
    public void consumerMessage(ConsumerRecord<String, MemberPoint> record, Acknowledgment acknowledgment) throws InterruptedException {
        log.debug("topic:{}, headers: {}, partition: {}, offset: {}, value: {} ",
                record.topic(), record.headers().toArray(),
                record.partition(), record.offset(),
                record.value());
        try {
            MemberPoint point = record.value();
            if (pointService.savePoint(point)) {
                throw new Exception("test");
            }
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // todo: 최대 재시도 횟수가 필요해 보임.
            seekCallback.get().seek(record.topic(), record.partition(), record.offset());
            Thread.sleep(3000);
        }
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {

        seekCallback.set(callback);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.forEach((k,v) -> callback.seekToBeginning(k.topic(), k.partition()));
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

    }

}
