package dev.njegovan.copyandsync.service;

import dev.njegovan.copyandsync.config.ApplicationProperties;
import dev.njegovan.copyandsync.consumer.Consumer;
import dev.njegovan.copyandsync.consumer.ConsumerThread;
import dev.njegovan.copyandsync.producer.Producer;
import dev.njegovan.copyandsync.producer.ProducerThread;
import dev.njegovan.copyandsync.wrapper.ByteArrayWrapper;
import dev.njegovan.copyandsync.utils.LoggingUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Log4j2
@Service
public class FileCopyingService {
    private final ApplicationProperties applicationProperties;

    public FileCopyingService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void execute() throws IOException, InterruptedException {
        File source = new File(applicationProperties.getInputFile());
        File dest = new File(applicationProperties.getOutputFile());

        if (Files.deleteIfExists(dest.toPath())) {
            LoggingUtils.printFileHasBeenDeleted(dest);
        }

        FileInputStream inputStream = new FileInputStream(source);

        final long totalSize = inputStream.getChannel().size();
        final byte[] dataBuffer = new byte[applicationProperties.getDataBufferMaxValue()];
        final int[] iteration = new int[1];
        final int[] count = new int[1];
        final long[] pct = new long[1];
        final long[] startTime = new long[1];


        final BlockingQueue<ByteArrayWrapper> blockingQueue = new ArrayBlockingQueue<>(applicationProperties.getDataBufferMaxValue(), true);
        final ByteArrayWrapper endByteArrayWrapper = new ByteArrayWrapper(new byte[]{});

        Consumer consumer = new Consumer(blockingQueue, endByteArrayWrapper);
        Thread consumerThread = new ConsumerThread(consumer, dest, startTime, iteration);
        consumerThread.setName("Consumer Thread");

        Producer producer = new Producer(blockingQueue, endByteArrayWrapper);
        Thread producerThread = new ProducerThread(producer, dataBuffer, startTime, inputStream, count);
        producerThread.setName("Producer Thread");

        System.out.println("Start copying...");
        consumerThread.start();
        producerThread.start();

        while (consumerThread.isAlive()) {
            LoggingUtils.printPct(iteration, totalSize, pct, applicationProperties.getDataBufferMaxValue());
        }

        consumerThread.join();
        producerThread.join();
    }
}
