package dev.njegovan.copyandsync.producer;

import dev.njegovan.copyandsync.utils.LoggingUtils;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

@Log4j2
public class ProducerThread extends Thread {
    private final Producer producer;
    private final byte[] dataBuffer;
    private final long[] startTime;
    private final FileInputStream inputStream;
    private final int[] count;

    public ProducerThread(Producer producer, byte[] dataBuffer, long[] startTime, FileInputStream inputStream, int[] count) {
        this.producer = producer;
        this.dataBuffer = dataBuffer;
        this.startTime = startTime;
        this.inputStream = inputStream;
        this.count = count;
    }

    @Override
    public void run() {
        startTime[0] = System.nanoTime();

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            while ((count[0] = bufferedInputStream.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                producer.produce(dataBuffer, count[0]);
            }

            producer.addEndByteArrayWrapper();
        } catch (Exception e) {
            log.error("Producer error: {}", e.getMessage(), e);
        }

        LoggingUtils.printEndProducing();
    }
}
