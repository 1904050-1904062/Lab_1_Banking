import java.util.List;
import java.util.ArrayList;

public class OperationsQueue {
    private final List<Integer> operations = new ArrayList<>();
    private boolean running = true;

    public void addSimulation(int totalSimulation) {
        for (int i = 0; i < totalSimulation; i++) {
            int random;
            do {
                random = (int) (Math.random() * 200) - 100;
            } while (random == 0);
            add(random);
            System.out.println(i + ". New operation added: " + random);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        add(-9999);
    }

    public synchronized void add(int amount) {
        operations.add(amount);
        notifyAll();
    }

    public synchronized int getNextItem() {
        while (operations.isEmpty() && running) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!running && operations.isEmpty()) {
            return -9999;
        }
        return operations.remove(0);
    }

    public synchronized void stop() {
        running = false;
        notifyAll();
    }
}
