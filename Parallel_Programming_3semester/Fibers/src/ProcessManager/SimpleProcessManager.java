package ProcessManager;

import Fibers.Fiber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleProcessManager {

    private static List<Fiber> fibers = new ArrayList<>();
    private static List<Fiber> listForDelete = new ArrayList<>();
    private static Fiber currentFiber;
    private static boolean started = false;
    private static Random random = new Random();

    public static void start() {
        if (!started) {
            if (!fibers.isEmpty()){
                started = true;
                switchProcess(false);
            }
        }
    }

    public static void submit(Process process) {
        fibers.add(new Fiber(process::run));
    }

    private static Fiber getNextFiber() {
        Fiber res;
        res = fibers.get(random.nextInt(fibers.size()));
        return res;
    }


    public static void switchProcess(boolean isFinished) {
        if (!isFinished) {
            if (fibers.size() > 1 && fibers.contains(currentFiber)) {
                Fiber tmp = currentFiber;
                fibers.remove(currentFiber);
                currentFiber = getNextFiber();
                fibers.add(tmp);
            } else {
                currentFiber = getNextFiber();
            }
            Fiber.switchTo(currentFiber.getID());
        } else {
            if (!currentFiber.isPrimary()) {
                listForDelete.add(currentFiber);
            }
            fibers.remove(currentFiber);
            if (!fibers.isEmpty()) {
                switchProcess(false);
            } else {
                Fiber.switchTo(Fiber.getPrimaryID());
                for (Fiber fiber : listForDelete) {
                    Fiber.delete(fiber.getID());
                }
            }
        }
    }
}
