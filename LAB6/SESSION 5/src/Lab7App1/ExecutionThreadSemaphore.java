package Lab7App1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ExecutionThreadSemaphore extends Thread {

    private CountDownLatch latch;
    public Semaphore sem1, sem2;

    CyclicBarrier barrier;
    int sleep_min, sleep_max;
    int [] activity1, activity2;

    public ExecutionThreadSemaphore(Semaphore sem1, Semaphore sem2, CountDownLatch latch, int sleep_min, int sleep_max, int[] activity1, int[] activity2) {

        this.latch = latch;
        this.sem2 = sem2;
        this.sem1 = sem1;

        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;
        this.activity2 = activity2;

    }

    public void run()
    {
        System.out.println(this.getName() + " - State 1");
        int k = (int) Math.round(Math.random()*(activity1[1]- activity1[1]) + activity1[0]);

        for (int i = 0; i < k * 100000; i++) {

            i++;
            i--;

        }

       //sem1.tryacquire - when you have deadlock
            sem1.tryAcquire();
            System.out.println(this.getName() + " - State 2");
            k = (int) Math.round(Math.random()*(activity2[1]- activity2[0]) + activity2[0]);

            for (int i = 0; i < k * 100000; i++) {

                i++;
                i--;
            }

                sem2.tryAcquire();
                System.out.println(this.getName() + " - State 3");
                try {

                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

                } catch (InterruptedException e) {

                      e.printStackTrace();
                }


            sem2.release();

        sem1.release();

            latch.countDown();
        try{
            latch.await();
        } catch (InterruptedException  e)
        {
            e.printStackTrace();
        }

    }


}

class Mains{
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException{
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(1);

        int[] activity1 = {2, 4};
        int[] activity2 = {4,6};
        int[] activity3 = {3, 5};
        int[] activity4 = {5, 7};


        CountDownLatch latch = new CountDownLatch(4);

        while(true)
        {
            new ExecutionThreadSemaphore( sem1, sem2, latch,  4, 4, activity1, activity2 ).start();
            new ExecutionThreadSemaphore(sem1, sem2, latch, 4, 4, activity3, activity4 ).start();

            latch.countDown();
            latch.await();

        }

    }
}
