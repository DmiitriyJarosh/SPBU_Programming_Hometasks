package ProcessManager;

import Fibers.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ProcessManager.Main.procManager;

public class Process implements Action {
    private static Random random = new Random();

    private static final int LONG_PAUSE_BOUNDARY = 100;
    private static final int SHORT_PAUSE_BOUNDARY = 10;
    private static final int WORK_BOUNDARY = 1000;
    private static final int INTERVALS_AMOUNT_BOUNDARY = 3;
    public static final int PRIORITY_LEVELS_NUMBER = 10;

    private List<Integer> workIntervals = new ArrayList<>();
    private List<Integer> pauseIntervals = new ArrayList<>();

    private int priority;

    public Process() {
        int amount = random.nextInt(INTERVALS_AMOUNT_BOUNDARY);
        for (int i = 0; i < amount; i++) {
            workIntervals.add(random.nextInt(WORK_BOUNDARY));
            pauseIntervals.add(random.nextInt(random.nextDouble() > 0.9 ? LONG_PAUSE_BOUNDARY : SHORT_PAUSE_BOUNDARY));
        }
        priority = random.nextInt(PRIORITY_LEVELS_NUMBER);
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < workIntervals.size(); i++) {
            Thread.sleep(workIntervals.get(i)); // work emulation
            long pauseBeginTime = System.currentTimeMillis();
            do {
                procManager.switchProcess(false);
            } while (System.currentTimeMillis() - pauseBeginTime < pauseIntervals.get(i)); // I/O emulation
        }
        //System.out.println("!!! FINISHED !!!");
        procManager.switchProcess(true);
    }

    public int getPriority() {
        return priority;
    }

    public int getTotalDuration() {
        return getActiveDuration() + pauseIntervals.stream().mapToInt(Integer::intValue).sum();
    }

    public int getActiveDuration() {
        return workIntervals.stream().mapToInt(Integer::intValue).sum();
    }
}
