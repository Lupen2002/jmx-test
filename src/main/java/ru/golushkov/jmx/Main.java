package ru.golushkov.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ThreadController controller = new ThreadController(server);
        server.registerMBean(controller, new ObjectName("app:name=Thread Controller"));
        controller.run();
    }
}
