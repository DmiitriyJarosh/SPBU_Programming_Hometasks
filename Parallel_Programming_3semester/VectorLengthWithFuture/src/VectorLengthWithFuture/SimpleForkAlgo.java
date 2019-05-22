package VectorLengthWithFuture;

import java.util.concurrent.RecursiveTask;

public class SimpleForkAlgo implements IVectorLengthComputer {

    @Override
    public int ComputeLength(int[] a) {
        try {
            Sum left = new Sum(a, true);
            left.fork();
            Sum right = new Sum(a, false);
            right.fork();
            return (int)Math.sqrt(left.join() + right.join());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}

class Sum extends RecursiveTask<Integer>{

    private int[] a;
    private boolean leftPart;

    public Sum(int[] a, boolean leftPart) {
        this.a = a;
        this.leftPart = leftPart;
    }

    @Override
    protected Integer compute() {
        if (leftPart) {
            int sum = 0;
            for (int i = 0; i < a.length / 2; i++) {
                sum += a[i] * a[i];
            }
            return sum;
        } else {
            int sum = 0;
            for (int i = a.length / 2; i < a.length; i++) {
                sum += a[i] * a[i];
            }
            return sum;
        }
    }
}
