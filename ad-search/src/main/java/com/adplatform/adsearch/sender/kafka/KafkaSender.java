package com.adplatform.adsearch.sender.kafka;

import com.adplatform.adsearch.mysql.dto.MySqlRowData;
import com.adplatform.adsearch.sender.ISender;
import com.alibaba.fastjson2.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        this.kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            System.out.println("kafka processMysqlRowData: " +
            JSON.toJSONString(rowData));
        }
    }
}
