package ru.golushkov.jmx;

import com.codahale.metrics.Meter;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleThread implements Runnable, SimpleThreadMBean{
    private final AtomicBoolean isRun = new AtomicBoolean(true);
    private final Meter meter = new Meter();

    @Override
    public void run() {
        while (isRun.get()) {
            meter.mark();
        }
    }

    @Override
    public double getOneMinuteRate() {
        return meter.getOneMinuteRate();
    }

    @Override
    public void stop() {
        isRun.set(false);
    }
}
