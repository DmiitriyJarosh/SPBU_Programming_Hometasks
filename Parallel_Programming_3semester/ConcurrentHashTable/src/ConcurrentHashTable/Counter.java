package ConcurrentHashTable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private volatile int value;
    private Lock lock;

    public Counter() {
        lock = new ReentrantLock();
        value = 0;
    }

    public void inc() {
        try {
            lock.lock();
            value++;
        } finally {
            lock.unlock();
        }
    }
}
