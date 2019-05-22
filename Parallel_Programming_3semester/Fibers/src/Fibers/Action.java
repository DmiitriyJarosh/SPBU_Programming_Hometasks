package Fibers;

@FunctionalInterface
public interface Action {
    void run() throws InterruptedException;
}
