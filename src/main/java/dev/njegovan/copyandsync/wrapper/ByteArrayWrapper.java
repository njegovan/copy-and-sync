package dev.njegovan.copyandsync.wrapper;

import lombok.Data;

import java.util.Arrays;

@Data
public class ByteArrayWrapper {
    final private byte[] data;
    private int bytesReadCount;

    public ByteArrayWrapper(byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }
}
