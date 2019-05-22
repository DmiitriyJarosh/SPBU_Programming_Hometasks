package com.company;

public class TestClass implements Runnable {

    private Counter counter;

    public TestClass(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.inc();
        }
    }
}