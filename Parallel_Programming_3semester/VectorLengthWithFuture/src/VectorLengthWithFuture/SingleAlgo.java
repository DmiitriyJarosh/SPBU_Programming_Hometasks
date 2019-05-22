package VectorLengthWithFuture;

public class SingleAlgo implements IVectorLengthComputer {

    @Override
    public int ComputeLength(int[] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * a[i];
        }
        return (int)Math.sqrt(sum);
    }
}
