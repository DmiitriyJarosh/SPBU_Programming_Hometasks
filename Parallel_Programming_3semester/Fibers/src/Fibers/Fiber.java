package Fibers;

public class Fiber {

    private Action action;
    private int ID;
    private static int primaryID = 0;
    private boolean isPrimary;

    public int getID() {
        return ID;
    }

    public static int getPrimaryID() {
        return primaryID;
    }

    public boolean isPrimary()
    {
        return isPrimary;
    }


    public Fiber(Action action) {
        innerCreate(action);
    }

    public void delete() {
        FibersJavaAPI.deleteFiber(ID);
    }

    public static void delete(int fiberId) {
        FibersJavaAPI.deleteFiber(fiberId);
    }

    public static void switchTo(int fiberId) {
        System.out.println("Current fiber switch to fiber #" + fiberId);
        FibersJavaAPI.switchToFiber(fiberId);
    }

    private void innerCreate(Action action) {
        this.action = action;
        if (primaryID == 0) {
            primaryID = FibersJavaAPI.convertThreadToFiber(0);
            isPrimary = true;
            //System.out.println("PrimaryId: " + primaryID);
        }
        ID = FibersJavaAPI.createFiber(100500, this::fiberRunnerProc, 0);
    }

    private int fiberRunnerProc(int lpParam) throws InterruptedException {
        int status = 0;
        try {
            action.run();
        } catch (Exception e) {
            status = 1;
            throw e;
        } finally {
            if (status == 1) {
                FibersJavaAPI.deleteFiber(ID);
            }
        }
        return status;
    }
}
