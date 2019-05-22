package VectorLengthWithFuture;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final int AMOUNT_OF_DIMENSIONS = 10000000;
    private static final int NUM_OF_ITERATIONS = 10000;

    public static void main(String[] args) {
        Random random = new Random();
        int[] a = new int[AMOUNT_OF_DIMENSIONS];
        for (int i = 0; i < AMOUNT_OF_DIMENSIONS; i++) {
            a[i] = random.nextInt(10);
        }
        ExecutorService ex2 = Executors.newFixedThreadPool(8);
        long singleTime = 0;
        long futureTime = 0;
        long forkTime = 0;
        long startTime, stopTime;
        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            startTime = System.currentTimeMillis();
            int singleRes = new SingleAlgo().ComputeLength(a);
            stopTime = System.currentTimeMillis();
            singleTime += stopTime - startTime;
        }

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            startTime = System.currentTimeMillis();
            int forkRes = new SimpleForkAlgo().ComputeLength(a);
            stopTime = System.currentTimeMillis();
            forkTime += stopTime - startTime;
        }

        for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
            startTime = System.currentTimeMillis();
            int futureRes = new FutureAlgo(ex2).ComputeLength(a);
            stopTime = System.currentTimeMillis();
            futureTime += stopTime - startTime;
        }
        System.out.println("Average time:\nSingle: " + (singleTime / (double)NUM_OF_ITERATIONS) + "\nFuture: " + (futureTime / (double)NUM_OF_ITERATIONS) + "\nFork: " + (forkTime / (double)NUM_OF_ITERATIONS));
        ex2.shutdown();
    }
}
