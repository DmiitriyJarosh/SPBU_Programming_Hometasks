package com.company;

public class Main {

    public static void main(String[] args) {
	    MyThreadPool pool = new MyThreadPool();
	    Counter counter = new Counter();
	    for (int i = 0; i < 10; i++) {
	        pool.execute(new TestClass(counter));
        }
        Object obj = new Object();
        try {
            synchronized (obj) {
                obj.wait(100);
            }
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
        pool.close();
	    System.out.println(counter.getAcc());
    }
}


