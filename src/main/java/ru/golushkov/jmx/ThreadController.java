package ru.golushkov.jmx;

import javax.management.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadController implements ThreadControllerMBean, Runnable {

    private final List<SimpleThreadMBean> threads = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final AtomicBoolean isRun = new AtomicBoolean(true);
    private final MBeanServer server;

    private String name = "ThreadController";
    private int next = 1;

    public ThreadController(MBeanServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCount() {
        return threads.size();
    }

    @Override
    public void add() {
        final SimpleThread thread = new SimpleThread();
        try {
            final ObjectName name = new ObjectName(String.format("app:type=threads,name=Simple Thread %d", next));
            server.registerMBean(thread, name);
            threads.add(thread);
            thread.onStop((stopped) -> {
                threads.remove(stopped);
                try {
                    server.unregisterMBean(name);
                } catch (InstanceNotFoundException | MBeanRegistrationException e) {
                    e.printStackTrace();
                }
            });
            executor.execute(thread);
            next++;
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | MalformedObjectNameException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        this.isRun.set(false);
    }

    @Override
    public void run() {
        try {
            while (this.isRun.get()) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for (SimpleThreadMBean thread: this.threads) {
                thread.stop();
            }
            executor.shutdown();
        }
    }
}
