package L7App4;

import java.util.concurrent.Semaphore;

public class ExecutionThread extends Thread {

    final Semaphore semaphore;
    int activitymax, activitymin;
    int sleep;

    public ExecutionThread(int sleep, Semaphore semaphore, int activitymax, int activitymin) {
        this.semaphore = semaphore;
        this.activitymax = activitymax;
        this.activitymin = activitymin;
        this.sleep = sleep;
    }

    public void run() {

        System.out.println(this.getName() + " - STATE 1");
        try {
            semaphore.acquire(2);
            System.out.println(this.getName() + " - STATE 2");
            int k = (int) Math.round(Math.random() * (activitymax - activitymin) + activitymax);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }
            semaphore.release(2);
            System.out.println(this.getName() + " - STATE 3");
            Thread.sleep(sleep);
            System.out.println(this.getName() + " - STATE 4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Main {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);

        ExecutionThread thread1 = new ExecutionThread(500, semaphore, 5, 1);
        ExecutionThread thread2 = new ExecutionThread(500, semaphore, 10, 3);
        ExecutionThread thread3 = new ExecutionThread(500, semaphore, 7, 2);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
