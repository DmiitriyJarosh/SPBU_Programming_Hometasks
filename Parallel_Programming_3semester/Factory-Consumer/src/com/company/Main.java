package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int NUM_OF_CONSUMERS = 10;
    private static final int NUM_OF_FACTORIES = 2;


    public static void main(String[] args) {
        Consumer[] consumers = new Consumer[NUM_OF_CONSUMERS];
        Factory[] factories = new Factory[NUM_OF_FACTORIES];
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < NUM_OF_CONSUMERS; i++) {
            consumers[i] = new Consumer(list);
            new Thread(consumers[i]).start();
        }

        for (int i = 0; i < NUM_OF_FACTORIES; i++) {
            factories[i] = new Factory(list);
            new Thread(factories[i]).start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < NUM_OF_CONSUMERS; i++) {
            consumers[i].setStopFlag(true);
        }

        for (int i = 0; i < NUM_OF_FACTORIES; i++) {
            factories[i].setStopFlag(true);
        }
    }
}
