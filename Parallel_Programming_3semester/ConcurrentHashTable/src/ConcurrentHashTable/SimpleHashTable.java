package ConcurrentHashTable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleHashTable implements IExamSystem {

    private volatile ArrayList<Exam>[] table;
    private Lock[] locks;
    private int size;
    private AtomicInteger amountOfElems;
    private static final int DEFAULT_SIZE = 10000;

    public SimpleHashTable() {
        size = DEFAULT_SIZE;
        amountOfElems = new AtomicInteger(0);
        table = new ArrayList[DEFAULT_SIZE];
        locks = new Lock[DEFAULT_SIZE];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<>();
            locks[i] = new ReentrantLock();
        }
    }

//    public int getSize() {
//        return size;
//    }

    public void resize(boolean increaseFlag) {
        Lock[] tmp = locks;
        //System.out.println(tmp.length + " ! " + locks.length);
        try {
            for (int i = 0; i < locks.length; i++) {
                //System.out.println(locks.length);
                tmp[i].lock();
            }
            //System.out.println("$$$");
            if (increaseFlag) {
                int newSize = size * 4;
                ArrayList<Exam>[] newTable = new ArrayList[newSize];
                Lock[] newLocks = new Lock[newSize];
                for (int i = 0; i < newSize; i++) {
                    newLocks[i] = new ReentrantLock();
                    newTable[i] = new ArrayList<>();
                }
                for (int i = 0; i < table.length; i++) {
                    for (Exam exam : table[i]) {
                        newTable[hash(exam.getStudentId(), newSize)].add(exam);
                    }
                }
                locks = newLocks;
                table = newTable;
                size = newSize;
            } else {
                int newSize = size / 2;
                if (newSize <= DEFAULT_SIZE) {
                    newSize = DEFAULT_SIZE;
                }
                if (newSize == size) {
                    return;
                }
                ArrayList<Exam>[] newTable = new ArrayList[newSize];
                Lock[] newLocks = new Lock[newSize];
                for (int i = 0; i < newSize; i++) {
                    newLocks[i] = new ReentrantLock();
                    newTable[i] = new ArrayList<>();
                }
                for (int i = 0; i < table.length; i++) {
                    for (Exam exam : table[i]) {
                        newTable[hash(exam.getStudentId(), newSize)].add(exam);
                    }
                }
                locks = newLocks;
                table = newTable;
                size = newSize;
            }

        } finally {
            //System.out.println("%%%");
            for (int i = 0; i < tmp.length; i++) {
                tmp[i].unlock();
            }
        }
    }

    public int hash(long studentId, int size) {
        return Long.toString(studentId).hashCode() % size;
    }


    @Override
    public boolean Contains(long studentId, long courseId) {
        int hash = hash(studentId, size);
        boolean lockFlag = false;
        Lock lock = locks[hash];
        try {
            lock.lock();
            if (hash(studentId, size) != hash) {
                lock.unlock();
                lockFlag = true;
                return Contains(studentId, courseId);
            }
            return table[hash].contains(new Exam(studentId, courseId));
        } finally {
            if (!lockFlag) {
                lock.unlock();
            }
        }

    }

    @Override
    public void Add(long studentId, long courseId) {
        int hash = hash(studentId, size);
        boolean lockFlag = false;
        Lock lock = locks[hash];
        try {
            lock.lock();
            if (hash(studentId, size) != hash) {
                lock.unlock();
                lockFlag = true;
                Add(studentId, courseId);
                return;
            }
            if (!table[hash].contains(new Exam(studentId, courseId))) {
                table[hash].add(new Exam(studentId, courseId));
                amountOfElems.getAndIncrement();
            }
        } finally {
            if (!lockFlag) {
                lock.unlock();
            }
            if (amountOfElems.get() * 2 >= size) {
                resize(true);
            }
        }
    }

    @Override
    public void Remove(long studentId, long courseId) {
        int hash = hash(studentId, size);
        boolean lockFlag = false;
        Lock lock = locks[hash];
        try {
            lock.lock();
            if (hash(studentId, size) != hash) {
                lock.unlock();
                lockFlag = true;
                Remove(studentId, courseId);
                return;
            }
            if (table[hash].contains(new Exam(studentId, courseId))) {
                table[hash].remove(new Exam(studentId, courseId));
                amountOfElems.getAndDecrement();
            }
        } finally {
            if (!lockFlag) {
                lock.unlock();
            }
            if (amountOfElems.get() * 4 <= size) {
                resize(false);
            }
        }
    }
}
