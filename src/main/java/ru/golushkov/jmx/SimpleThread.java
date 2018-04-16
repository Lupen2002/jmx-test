package ru.golushkov.jmx;

import com.codahale.metrics.Meter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SimpleThread implements Runnable, SimpleThreadMBean{
    private final AtomicBoolean isRun = new AtomicBoolean(true);
    private final Meter meter = new Meter();
    private final List<Consumer<SimpleThread>> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        try {
            while (isRun.get()) {
                Thread.sleep(100);
                meter.mark();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onStop(Consumer<SimpleThread> listener) {
        listeners.add(listener);
    }

    @Override
    public double getOneMinuteRate() {
        return meter.getOneMinuteRate();
    }

    @Override
    public void stop() {
        isRun.set(false);
        for(Consumer<SimpleThread> listener: this.listeners) {
            listener.accept(this);
        }
    }
}
