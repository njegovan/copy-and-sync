package dev.njegovan.copyandsync.producer;

import dev.njegovan.copyandsync.wrapper.ByteArrayWrapper;

import java.util.concurrent.BlockingQueue;

public class Producer {
    private final BlockingQueue<ByteArrayWrapper> blockingQueue;
    private final ByteArrayWrapper endByteArrayWrapper;

    public Producer(BlockingQueue<ByteArrayWrapper> blockingQueue, ByteArrayWrapper endByteArrayWrapper) {
        this.blockingQueue = blockingQueue;
        this.endByteArrayWrapper = endByteArrayWrapper;
    }

    public void produce(byte[] dataBuffer, final int count) throws InterruptedException {
        ByteArrayWrapper byteArrayWrapper = new ByteArrayWrapper(dataBuffer);
        byteArrayWrapper.setBytesReadCount(count);
        blockingQueue.put(byteArrayWrapper);
    }

    public void addEndByteArrayWrapper() {
        blockingQueue.add(endByteArrayWrapper);
    }
}
