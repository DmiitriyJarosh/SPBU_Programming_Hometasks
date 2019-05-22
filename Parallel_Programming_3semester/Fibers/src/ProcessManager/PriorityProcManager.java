package ProcessManager;

import Fibers.Fiber;

import java.util.ArrayList;
import java.util.List;

public class PriorityProcManager {

    private static PriorityProcManager instance = new PriorityProcManager();

    private static List<Fiber> fibers = new ArrayList<>();
    private static final int DEFAULT_PRIORITY_CHANGE_VALUE = 1;
    private static List<Integer> priorities = new ArrayList<>();
    private static List<Fiber> listForDelete = new ArrayList<>();
    private static Fiber currentFiber;
    private static boolean started = false;

    public static void start() {
        if (!started) {
            if (!fibers.isEmpty()){
                started = true;
                switchProcess(false);
            }
        }
    }

    public static PriorityProcManager getInstance() {
        return instance;
    }

    public static void submit(Process process) {
        fibers.add(new Fiber(process::run));
        priorities.add(process.getPriority());
    }

    private static Fiber getNextFiber() {
        int max = -1;
        int iter = 0;
        for (int i = 0; i < priorities.size(); i++) {
            if (priorities.get(i) > max) {
                max = priorities.get(i);
                iter = i;
            }
            priorities.set(i, priorities.get(i) + DEFAULT_PRIORITY_CHANGE_VALUE);
        }
        priorities.set(iter, max - DEFAULT_PRIORITY_CHANGE_VALUE);
        return fibers.get(iter);
    }

    public static void switchProcess(boolean isFinished) {
        if (!isFinished) {
            if (fibers.size() > 1 && fibers.contains(currentFiber)) {
                Fiber tmp = currentFiber;
                int priority = priorities.get(fibers.indexOf(currentFiber));
                priorities.remove(fibers.indexOf(currentFiber));
                fibers.remove(currentFiber);
                currentFiber = getNextFiber();
                fibers.add(tmp);
                priorities.add(priority);
            } else {
                currentFiber = getNextFiber();
            }
            Fiber.switchTo(currentFiber.getID());
        } else {
            if (!currentFiber.isPrimary()) {
                listForDelete.add(currentFiber);
            }
            priorities.remove(fibers.indexOf(currentFiber));
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
