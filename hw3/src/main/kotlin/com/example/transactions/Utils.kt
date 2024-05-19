package com.example.transactions

import org.apache.kafka.clients.admin.Admin
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

object Utils {

    private const val BOOTSTRAP_SERVERS = "localhost:9091"

    fun createTopics(topics: List<String>) {
        createAdmin().use {
            it.createTopics(
                topics.map { topic -> NewTopic(topic, 1, 1) }
            )
        }
    }

    private fun createAdmin(): Admin = Admin.create(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS))

    fun createProducer(): KafkaProducer<String, String> =
        KafkaProducer<String, String>(
            mapOf<String, Any>(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS,
                ProducerConfig.ACKS_CONFIG to "all",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.TRANSACTIONAL_ID_CONFIG to "transactions",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to "true",
            )
        )

    fun createConsumer(): KafkaConsumer<String, String> =
        KafkaConsumer<String, String>(
            mapOf<String, Any>(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
                ConsumerConfig.GROUP_ID_CONFIG to "transactions",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.ISOLATION_LEVEL_CONFIG to "read_committed"
            )
        )
}
