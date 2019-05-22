package com.company;

import java.util.List;

public class Consumer implements Runnable{
    private int product;
    private Boolean stopFlag;
    private final List<Integer> list;

    public Consumer (List<Integer> list) {
        this.list = list;
        stopFlag = false;
    }

    private boolean popProduct() {
        synchronized (list) {
            if (list.size() > 0) {
                product = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                return true;
            }
            return false;
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
                    if (popProduct()) {
                        System.out.println(product);
                    }
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
