package Fibers;

public class FibersJavaAPI {

    static {
        System.loadLibrary("rawFibers");
    }

    public static native int convertThreadToFiber(int lpParameter);

    public static native void switchToFiber(int lpFiber);

    public static native void deleteFiber(int lpFiber);

    public static native int createFiber(int dwStackSize, FiberStartRoutine lpStartAddress, int lpParameter);
}
