package ProcessManager;

public class Main {

    public static final PriorityProcManager procManager = PriorityProcManager.getInstance();

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
           procManager.submit(new Process());
        }
        procManager.start();
    }
}
