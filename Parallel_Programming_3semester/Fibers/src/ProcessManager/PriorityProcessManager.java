package ProcessManager;

import Fibers.Fiber;

import java.util.ArrayList;
import java.util.List;

public class PriorityProcessManager {
    private static List<Fiber> fibers = new ArrayList<>();
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

    public static void submit(Process process) {
        fibers.add(new Fiber(process::run));
        priorities.add(process.getPriority());
    }

    private static Fiber getNextFiber() {
        int max = -1;
        for (Integer priority : priorities) {
            if (priority > max) {
                max = priority;
            }
        }
        return fibers.get(priorities.indexOf(max));
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
