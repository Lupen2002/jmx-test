package ru.golushkov.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        SimpleThread thread = new SimpleThread();
        server.registerMBean(thread, new ObjectName("app:name=Simple Thread"));
        thread.run();
    }
}
