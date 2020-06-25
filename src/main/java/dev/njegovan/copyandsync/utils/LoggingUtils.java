package dev.njegovan.copyandsync.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Log4j2
@UtilityClass
public class LoggingUtils {

    public void printFileHasBeenDeleted(File dest) {
        String tname = Thread.currentThread().getName();
        System.out.println(tname + ": " + dest.getName() + " has been deleted.");
    }

    public void printEndProducing() {
        String tname = Thread.currentThread().getName();
        System.out.println(tname + ": " + "Done producing!");
    }

    public void printEndConsuming() {
        String tname = Thread.currentThread().getName();
        System.out.println(tname + ": " + "Done consuming!");
    }

    public void printPct(int[] iteration, long totalFileSize, long[] pct, int DATA_BUFFER_MAX_VALUE) {
        int count = iteration[0];
        long prevPct = pct[0];
        long part = count * DATA_BUFFER_MAX_VALUE;
        long calc = part * 100 / totalFileSize;
        if (calc != prevPct) {
            pct[0] = calc;
            Thread t = Thread.currentThread();
            System.out.println(t.getName() + ": " + pct[0] + "%");
        }
    }

    public void printEnd(final long[] startTime) {
        long endTime = System.nanoTime();
        String tname = Thread.currentThread().getName();

        System.out.println(tname + ": " + "Time consumption: " + calculate(TimeUnit.MILLISECONDS.convert(endTime - startTime[0], TimeUnit.NANOSECONDS)));
    }

    private String calculate(long milli) {
        Date date = new Date(milli);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}
