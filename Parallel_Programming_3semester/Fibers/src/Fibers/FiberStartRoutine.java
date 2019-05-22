package Fibers;

@FunctionalInterface
public interface FiberStartRoutine {
    int start(int param) throws InterruptedException;
}
