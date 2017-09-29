package com.log.aggregator.runner;

import com.log.aggregator.consumer.LogConsumerService;
import com.log.aggregator.producer.LogProducerService;
import com.log.aggregator.utils.QDestination;

/**
 * Created by yashkhandelwal
 */
public class LogRunner {

    public static void main(String[] args) throws InterruptedException {

        LogProducerService producerService = new LogProducerService();

        producerService.startLogProducer(QDestination.APP1_QUEUE.getDestinationName());
        Thread.sleep(100);
        LogConsumerService consumerService = new LogConsumerService();
        consumerService.startLogConsumer(QDestination.APP1_QUEUE.getDestinationName());

        LogProducerService producerService2 = new LogProducerService();
        producerService2.startLogProducer(QDestination.APP2_QUEUE.getDestinationName());
        Thread.sleep(100);
        LogConsumerService consumerService2 = new LogConsumerService();
        consumerService2.startLogConsumer(QDestination.APP2_QUEUE.getDestinationName());

        LogProducerService producerService3 = new LogProducerService();
        producerService3.startLogProducer(QDestination.APP3_QUEUE.getDestinationName());
        Thread.sleep(100);
        LogConsumerService consumerService3 = new LogConsumerService();
        consumerService3.startLogConsumer(QDestination.APP3_QUEUE.getDestinationName());


        LogProducerService producerService4 = new LogProducerService();
        producerService4.startLogProducer(QDestination.APP4_QUEUE.getDestinationName());
        Thread.sleep(100);
        LogConsumerService consumerService4 = new LogConsumerService();
        consumerService4.startLogConsumer(QDestination.APP4_QUEUE.getDestinationName());

        LogProducerService producerService5 = new LogProducerService();
        producerService5.startLogProducer(QDestination.APP5_QUEUE.getDestinationName());
        Thread.sleep(100);
        LogConsumerService consumerService5 = new LogConsumerService();
        consumerService5.startLogConsumer(QDestination.APP5_QUEUE.getDestinationName());

    }
}
