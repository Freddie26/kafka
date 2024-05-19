package com.example.transactions

import com.example.transactions.Utils.createConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory.getLogger
import java.time.Duration

private val logger = getLogger("consumer")

fun main(args: Array<String>) {
    createConsumer().use { consumer ->
        consumer.subscribe(listOf("topic1", "topic2"))

        repeat(5) {
            val records = consumer.poll(Duration.ofSeconds(1))

            logger.info("Read {}", records.count())

            val topicToOffset = records.mapIndexed { index, record ->
                logger.info("[MY_CONSUMER][${record.topic()}][$index] ${record.value()}")
                val topic = TopicPartition(record.topic(), record.partition())
                val offset = OffsetAndMetadata(record.offset())
                topic to offset
            }.toMap()
            consumer.commitSync(topicToOffset)
        }
    }
}
