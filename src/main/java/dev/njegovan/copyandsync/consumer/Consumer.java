package dev.njegovan.copyandsync.consumer;

import dev.njegovan.copyandsync.wrapper.ByteArrayWrapper;
import dev.njegovan.copyandsync.utils.LoggingUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Consumer {
    private final BlockingQueue<ByteArrayWrapper> blockingQueue;
    private final ByteArrayWrapper endByteArrayWrapper;

    public Consumer(BlockingQueue<ByteArrayWrapper> blockingQueue, ByteArrayWrapper endByteArrayWrapper) {
        this.blockingQueue = blockingQueue;
        this.endByteArrayWrapper = endByteArrayWrapper;
    }

    public boolean consumeWithContinue(BufferedOutputStream bufferedOutputStream, final long[] startTime) throws IOException, InterruptedException {
        ByteArrayWrapper byteArrayWrapper = blockingQueue.take();

        if (endByteArrayWrapper == byteArrayWrapper) {
            LoggingUtils.printEnd(startTime);
            return false;
        }

        bufferedOutputStream.write(byteArrayWrapper.getData(), 0, byteArrayWrapper.getBytesReadCount());
        return true;
    }
}
