package VectorLengthWithFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FutureAlgo implements IVectorLengthComputer {

    private static final int NUM_OF_THREADS = 8;
    ExecutorService executor;
    Future<Integer>[] partRes;

    public FutureAlgo(ExecutorService executor) {
        this.executor = executor;
        partRes = new Future[NUM_OF_THREADS];
    }

    private Future<Integer> countPart(int[] a, int left, int right) {
        return executor.submit(() -> {
            int sum = 0;
            for (int i = left; i < right; i++) {
                sum += a[i] * a[i];
            }
            return sum;
        });
    }

    @Override
    public int ComputeLength(int[] a) {
        int sum = 0;
        for (int i = 0; i < NUM_OF_THREADS - 1; i++) {
            partRes[i] = countPart(a, a.length / NUM_OF_THREADS * i, a.length / NUM_OF_THREADS * (i + 1));
        }
        partRes[NUM_OF_THREADS - 1] = countPart(a, a.length / NUM_OF_THREADS * (NUM_OF_THREADS - 1), a.length);
        try {
            for (int i = 0; i < NUM_OF_THREADS; i++) {
                sum += partRes[i].get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int)Math.sqrt(sum);
    }
}