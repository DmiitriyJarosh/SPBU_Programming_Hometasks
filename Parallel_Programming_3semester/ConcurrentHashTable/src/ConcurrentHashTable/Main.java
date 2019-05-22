package ConcurrentHashTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        IExamSystem table = new SimpleHashTable();
        ExecutorService ex = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 9; i++) {
            ex.submit(new UserA(table, i));
        }
        ex.submit(new UserB(table));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ex.shutdown();
    }
}

class UserA implements Runnable {

    private IExamSystem table;
    int num;

    public UserA(IExamSystem table, int num) {
        this.table = table;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            for (int j = 0; j < 1000000; j++) {
                table.Add(num, num);
                for (int i = 0; i < 9; i++) {
                    table.Contains(num, num);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserB implements Runnable {

    private IExamSystem table;

    public UserB(IExamSystem table) {
        this.table = table;
    }

    @Override
    public void run() {
        try {
            for (int j = 0; j < 1000000; j++) {
                table.Remove(1, 1);
                for (int i = 0; i < 9; i++) {
                    System.out.println(i + " " + table.Contains(i, i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
