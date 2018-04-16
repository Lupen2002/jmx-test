package ru.golushkov.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("The application is running.\n" +
                "To manage the application, use jms or jconsole");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ThreadController controller = new ThreadController(server);
        server.registerMBean(controller, new ObjectName("app:name=Thread Controller"));
        controller.run();
    }
}
