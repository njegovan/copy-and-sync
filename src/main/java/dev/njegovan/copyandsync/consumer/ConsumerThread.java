package dev.njegovan.copyandsync.consumer;

import dev.njegovan.copyandsync.utils.LoggingUtils;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Log4j2
public class ConsumerThread extends Thread {
    private final Consumer consumer;
    private final File dest;
    private final long[] startTime;
    private final int[] iteration;

    public ConsumerThread(Consumer consumer, File dest, long[] startTime, int[] iteration) {
        this.consumer = consumer;
        this.dest = dest;
        this.startTime = startTime;
        this.iteration = iteration;
    }

    @Override
    public void run() {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dest, true))) {
            while (consumer.consumeWithContinue(bufferedOutputStream, startTime)) {
                iteration[0]++;
            }

            bufferedOutputStream.flush();
        }
        catch (Exception e) {
            log.error("Consumer error: {}", e.getMessage(), e);
        }

        LoggingUtils.printEndConsuming();
    }
}
