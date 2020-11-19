package com.practice.kafkaApp;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPracticeApplication.class, args);
	}

	@Autowired
	private KafkaProperties kafkaProperties;

	@Value("${tpd.verification-result-topic}")
	private String verificationResultTopic;
	@Value("${tpd.transfer-topic}")
	private String transferTopic;

	// Producer Configuration

	@Bean
	public Map<String, Object> producerConfigs(){
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return props;
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}

	// Consumer config

	@Bean
	public Map<String, Object> consumerConfig(){
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "tpd-logger");

		return props;
	}

	@Bean
	public ConsumerFactory<String, Object> consumerFactory(){
		final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
		jsonDeserializer.addTrustedPackages("*");

		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer);

	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

	@Bean
	public NewTopic transferTopic(){
		return new NewTopic(transferTopic, 3, (short) 1);
	}

	@Bean
	public NewTopic verificationResultTopic(){
		return new NewTopic(verificationResultTopic, 3, (short) 1);
	}

}
