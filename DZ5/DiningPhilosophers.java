import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Fork {
    private final Lock lock = new ReentrantLock();

    public boolean pickUp() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }
}

class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private int mealsEaten = 0;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (mealsEaten < 3) {
            think();
            if (id % 2 == 0) {
                eat(leftFork, rightFork);
            } else {
                eat(rightFork, leftFork);
            }
        }
        System.out.println("Философ " + id + " завершил трапезу.");
    }

    private void think() {
        System.out.println("Философ " + id + " думает.");
        try {
            Thread.sleep((int) (Math.random() * 1000)); // Время размышления
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat(Fork firstFork, Fork secondFork) {
        if (firstFork.pickUp()) {
            if (secondFork.pickUp()) {
                try {
                    System.out.println("Философ " + id + " ест. Прием пищи #" + (mealsEaten + 1));
                    Thread.sleep((int) (Math.random() * 1000));
                    mealsEaten++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                secondFork.putDown();
            }
            firstFork.putDown();
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        final int NUM_PHILOSOPHERS = 5;
        Fork[] forks = new Fork[NUM_PHILOSOPHERS];
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            new Thread(philosophers[i]).start();
        }
    }
}