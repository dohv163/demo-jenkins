//package com.example.demo.config.kafka;
//
//import java.security.Security;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import org.apache.kafka.clients.admin.Admin;
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.KafkaAdminClient;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.config.SaslConfigs;
//import org.apache.kafka.common.internals.Topic;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaAdmin;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//@EnableKafka
//@Configuration
//public class KafkaConfiguration {
//
//    @Value("${spring.kafka.admin.bootstrap-servers}")
//    private String bootstrapServerAdminClient;
//
//    @Value("${spring.kafka.producer.bootstrap-servers}")
//    private String bootstrapServerProducer;
//
//    @Value("${spring.kafka.producer.client-id}")
//    private String producerClientId;
//
//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String bootstrapServerConsumer;
//
//    @Value("${spring.kafka.consumer.client-id}")
//    private String consumerClientId;
//
//    @Value("${spring.kafka.admin.client-id}")
//    private String adminClientId;
//
//    @Value("${spring.kafka.sasl.jaas.config.username}")
//    private String username;
//
//    @Value("${spring.kafka.sasl.jaas.config.password}")
//    private String password;
//
//    @Value("${spring.kafka.sasl.mechanism}")
//    private String saslMechanism;
//
//    @Value("${spring.kafka.sasl.security.protocol}")
//    private String securityProtocol;
//
//    @Value("${spring.kafka.consumer.groupId}")
//    private String consumerGroupId;
//
//    @Value("${spring.kafka.consumer.auto-offset-reset}")
//    private String consumerAutoOffsetReset;
//
//    @Value("${spring.kafka.producer.topic-stream-enter-room}")
//    private String topicStreamEnterRoom;
//
//    @Value("${spring.kafka.producer.topic-stream-leave-room}")
//    private String topicStreamLeaveRoom;
//
//    @Value("${spring.kafka.producer.topic-close-stream-room}")
//    private String topicCloseStreamRoom;
//
//    @Value("${spring.kafka.producer.topic-stream-comment}")
//    private String topicStreamComment;
//
//    @Value("${spring.kafka.producer.topic-stream-join-giveaway}")
//    private String topicStreamJoinGA;
//
//    @Value("${spring.kafka.producer.topic-stream-join-vote}")
//    private String topicStreamJoinVote;
//
//    @Value("${spring.kafka.producer.topic-streamer-open-giveaway}")
//    private String topicOpenGiveaway;
//
//    @Value("${spring.kafka.producer.topic-request-cash-out}")
//    private String topicRequestCashOut;
//
//
//	  @Bean
//    public ProducerFactory<Object, Object> producerFactoryString() {
//        final Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerProducer);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, producerClientId);
//        configProps.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
//        configProps.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
//
//        String authen = "org.apache.kafka.common.security.plain.PlainLoginModule required "
//            + "username=\"" + username + "\""
//            + " password=\"" + password + "\";";
//
//        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, authen);
//
//      return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<Object, Object> kafkaTemplateString() {
//        return new KafkaTemplate<>(producerFactoryString());
//    }
//
//    @Bean
//    public ConsumerFactory<Object, Object> consumerFactory() {
//         final Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConsumer);
//        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerAutoOffsetReset);
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
//        configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerClientId);
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configProps.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
//        configProps.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
//
//        String authen = "org.apache.kafka.common.security.plain.PlainLoginModule required "
//            + "username=\"" + username + "\""
//            + " password=\"" + password + "\";";
//
//        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, authen);
//
//
//        return new DefaultKafkaConsumerFactory<>(configProps);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public KafkaAdmin admin() {
//
//        final Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServerAdminClient);
//        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, adminClientId);
//        configs.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
//        configs.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
//
//        String authen = "org.apache.kafka.common.security.plain.PlainLoginModule required "
//            + "username=\"" + username + "\""
//            + " password=\"" + password + "\";";
//        configs.put(SaslConfigs.SASL_JAAS_CONFIG, authen);
//        return new KafkaAdmin(configs);
//
//    }
//
//    @Bean
//    public NewTopic topicStreamEnterRoom() {
//        NewTopic topic = TopicBuilder.name(topicStreamEnterRoom)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicStreamLeaveRoom() {
//        NewTopic topic = TopicBuilder.name(topicStreamLeaveRoom)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicCloseStreamRoom() {
//        NewTopic topic = TopicBuilder.name(topicCloseStreamRoom)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicStreamComment() {
//        NewTopic topic = TopicBuilder.name(topicStreamComment)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicStreamJoinGA() {
//        NewTopic topic = TopicBuilder.name(topicStreamJoinGA)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicStreamJoinVote() {
//        NewTopic topic = TopicBuilder.name(topicStreamJoinVote)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicOpenGiveaway() {
//        NewTopic topic = TopicBuilder.name(topicOpenGiveaway)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//    @Bean
//    public NewTopic topicRequestCashOut() {
//        NewTopic topic = TopicBuilder.name(topicRequestCashOut)
//            .partitions(10)
//            .replicas(2)
//            .build();
//        return topic;
//    }
//
//}
