package org.dentaclean.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaAdminConfig {

    private final KafkaProperties properties;
    private AdminClient adminClient;

    private String nameTopic = "schedulle-topic";


    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        adminClient = AdminClient.create(configs);
        return new KafkaAdmin(configs);
    }

    @Bean
    public void deleteTopic() {
        deleteMessages(nameTopic, 0, 3);
    }

    public void deleteMessages(String topicName, int partitionIndex, int beforeIndex) {
        TopicPartition topicPartition = new TopicPartition(topicName, partitionIndex);
        Map<TopicPartition, RecordsToDelete> deleteMap = new HashMap<>();
        deleteMap.put(topicPartition, RecordsToDelete.beforeOffset(beforeIndex));
        adminClient.deleteRecords(deleteMap);
    }

    @Bean
    public NewTopic topicSchedulle() {
        return new NewTopic(nameTopic, 1, Short.valueOf("1"));
    }

}
