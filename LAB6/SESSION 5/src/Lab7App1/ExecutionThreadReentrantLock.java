package Lab7App1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThreadReentrantLock extends Thread {

    Integer monitor;

    ReentrantLock lock1, lock2;
    CyclicBarrier barrier;
    int sleep_min, sleep_max;

    int[]  activity1;
    int[]  activity2;


    public ExecutionThreadReentrantLock(ReentrantLock lock1, ReentrantLock lock2, int sleep_min, int sleep_max, int[] activity1, int[] activity2, CyclicBarrier barrier) {

        this.barrier = barrier;
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.monitor = monitor;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;
        this.activity2 = activity2;

    }

    public void run()
    {
        System.out.println(this.getName() + " - State 1");
        int k = (int) Math.round(Math.random()*(activity1[1]- activity1[0]) + activity1[0]);

        for (int i = 0; i < k * 100000; i++) {

            i++;
            i--;

        }

        if(lock1.tryLock())
        {
            try{
                System.out.println(this.getName() + " - State 2");
                 k = (int) Math.round(Math.random()*(activity2[1]- activity2[0]) + activity2[0]);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                if(lock2.tryLock())
                {
                    try{
                        System.out.println(this.getName() + " - State 3");
                        try {

                            Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

                        } catch (InterruptedException e) {

                              e.printStackTrace();
                        }

                    } finally {
                        lock2.unlock();
                    }
                }


            } finally {
                lock1.unlock();
            }

        }
        System.out.println(this.getName() + "- State 4");


        try{
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }
    }
}

class Main{
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException{
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        int[] activity1 = {2, 4};
        int[] activity2 = {4,6};
        int[] activity3 = {3, 5};
        int[] activity4 = {5, 7};


        CyclicBarrier barrier = new CyclicBarrier(3);

        while(true)
        {
            new ExecutionThreadReentrantLock(lock1, lock2, 4, 4, activity1, activity2, barrier ).start();
            new ExecutionThreadReentrantLock(lock1, lock2, 4, 4, activity3, activity4, barrier ).start();

            barrier.await();

            barrier.reset();

        }

    }
}
