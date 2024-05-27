package com.example.transactions

import com.example.transactions.Utils.createProducer
import com.example.transactions.Utils.createTopics
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory.getLogger

private val logger = getLogger("consumer")

fun main(args: Array<String>) {
    createTopics(listOf("topic1", "topic2"))

    createProducer().use { p ->
        p.initTransactions()
        p.beginTransaction()
        repeat(5) { index ->
            p.send(ProducerRecord("topic1", "commited transaction message $index"))
            p.send(ProducerRecord("topic2", "commited transaction message $index"))
        }
        p.commitTransaction()

        p.beginTransaction()
        repeat(2) { index ->
            p.send(ProducerRecord("topic1", "abort transaction message $index"))
            p.send(ProducerRecord("topic2", "abort transaction message $index"))
        }
        p.abortTransaction()
    }
}
