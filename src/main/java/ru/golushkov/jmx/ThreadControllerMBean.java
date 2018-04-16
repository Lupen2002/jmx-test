package ru.golushkov.jmx;

public interface ThreadControllerMBean {

    String getName();
    void setName(String name);

    int getCount();
    void add();
    void stop();
}
