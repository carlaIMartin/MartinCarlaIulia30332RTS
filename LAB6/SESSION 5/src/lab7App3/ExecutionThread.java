package lab7App3;

import java.util.concurrent.CountDownLatch;

public class ExecutionThread extends Thread {

    Integer monitor1, monitor2;

    CountDownLatch latch;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(Integer monitor1, Integer monitor2, CountDownLatch latch, int sleep_min, int sleep_max, int activity_min, int activity_max) {

        this.latch = latch;
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;

    }

    public void run()
    {
        System.out.println(this.getName() + " - State 1");

        try {

            Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

        } catch (InterruptedException e) {

              e.printStackTrace();
        }
        System.out.println(this.getName() + " - State 2");

        int k = (int) Math.round(Math.random()*(activity_max- activity_min) + activity_min);

        for (int i = 0; i < k * 100000; i++) {

            i++;
            i--;

        }

        synchronized(monitor1)
        {
            synchronized (monitor2)
            {
                monitor1.notify();
                monitor2.notify();

            }
        }

        System.out.println(this.getName() + " - State 3");

        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ExecutionThread2 extends Thread {
    Integer monitor1;

    CountDownLatch latch;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread2(Integer monitor1, CountDownLatch latch, int sleep_min, int sleep_max, int activity_min, int activity_max) {


        this.latch = latch;
        this.monitor1 = monitor1;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;

    }

    public void run(){
        System.out.println(this.getName() + " - State 1");
        synchronized (monitor1)
        {
            try {

                Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            try {
                monitor1.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            System.out.println(this.getName() + " - State 2");
            int k = (int) Math.round(Math.random()*(activity_max- activity_min) + activity_min);

            for (int i = 0; i < k * 100000; i++) {

                i++;
                i--;

            }
            System.out.println(this.getName() + " - State 3");

            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class Main{
    public static void main(String[] args) throws InterruptedException {
        Integer monitor1 = 1;
        Integer monitor2 = 2;


            CountDownLatch latch = new CountDownLatch(3);
            new ExecutionThread(monitor1, monitor2, latch,7,7,2,3).start();
            new ExecutionThread2(monitor1, latch,5,5,3,5).start();
            new ExecutionThread2(monitor2, latch,5,5,4,6).start();
            latch.countDown();
            latch.await();


    }
}

