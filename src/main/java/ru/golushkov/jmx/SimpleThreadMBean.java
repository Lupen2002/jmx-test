package ru.golushkov.jmx;

public interface SimpleThreadMBean {

    double getOneMinuteRate();

    void stop();
}
