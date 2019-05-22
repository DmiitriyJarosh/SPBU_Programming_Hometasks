package com.company;

import java.util.List;
import java.util.Random;

public class Factory implements Runnable {
    private int product;
    private final List<Integer> list;
    private Boolean stopFlag;
    private Random randomizer;

    public Factory(List<Integer> list) {
        this.product = 0;
        this.list = list;
        this.stopFlag = false;
        this.randomizer = new Random();
    }

    private void generateProduct() {
        product = randomizer.nextInt(1000);
    }

    private void pushProduct() {
        synchronized (list) {
            list.add(product);
        }
    }

    public void setStopFlag(Boolean stopFlag) {
        synchronized (stopFlag) {
            this.stopFlag = stopFlag;
        }
    }

    public void run() {
        while (true) {
            synchronized (stopFlag) {
                if (!stopFlag) {
                    generateProduct();
                    pushProduct();
                } else {
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
