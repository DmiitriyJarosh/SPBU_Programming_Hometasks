package com.company;

public class Counter {

    private int acc;

    public Counter() {
        acc = 0;
    }

    public synchronized void inc() {
        acc++;
    }

    public int getAcc() {
        return  acc;
    }
}
