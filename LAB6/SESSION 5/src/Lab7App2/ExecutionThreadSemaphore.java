package Lab7App2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThreadSemaphore extends Thread {

    Semaphore s1;

    ReentrantLock lock1, lock2;

    CountDownLatch latch;

    int sleep_min, sleep_max;
    int[] activity1, activity2;


    public ExecutionThreadSemaphore(Semaphore s1, CountDownLatch latch, int sleep_min, int sleep_max, int[] activity1) {

        this.latch = latch;
        this.s1 = s1;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;


    }

    public void run() {
        System.out.println(this.getName() + " - State 1");

        if (s1.tryAcquire()) {
            try {
                System.out.println(this.getName() + " - State 2");
                int k = (int) Math.round(Math.random() * (activity1[1] - activity1[0]) + activity1[0]);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                try {

                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            } finally {
                s1.release();
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


class ExecutionThreadSemaphore2 extends Thread {


    Semaphore s1, s2;

    ReentrantLock lock1, lock2;

    CountDownLatch latch;

    int sleep_min, sleep_max;
    int[] activity1, activity2;


    public ExecutionThreadSemaphore2(Semaphore s1, Semaphore s2, CountDownLatch latch, int sleep_min, int sleep_max, int[] activity1) {

        this.latch = latch;
        this.s1 = s1;
        this.s2=s2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;


    }

    public void run() {
        System.out.println(this.getName() + " - State 1");

        if(s1.tryAcquire())   //fara try
        {
            try{
                if(s2.tryAcquire()){
                    try{

            System.out.println(this.getName() + " - State 2");
            int k = (int) Math.round(Math.random() * (activity1[1] - activity1[0]) + activity1[0]);

            for (int i = 0; i < k * 100000; i++) {

                i++;
                i--;

            }

            try {

                Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
                    }finally{
                        s2.release();
                    }
                }
            }finally {
                s1.release();
            }
        }








        System.out.println(this.getName() + " - State 3");

        try {
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


class Main2{
    public static void main(String[] args) throws InterruptedException{

       Semaphore s1 = new Semaphore(1);
       Semaphore s2 = new Semaphore(1);
       int[] activity1 = {2, 4};
       int[] activity2 = {3, 6};
       int[] activity3 = {2, 5};

       CountDownLatch latch = new CountDownLatch(4);

       while(true)
       {
           new Lab7App2.ExecutionThreadSemaphore(s1,  latch, 4, 4, activity1 ).start();
           new Lab7App2.ExecutionThreadSemaphore2(s1, s2, latch, 3, 3, activity2).start();
           new ExecutionThreadSemaphore(s2, latch, 5, 5, activity3).start();


           latch.countDown();
           try {
               latch.await();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }


       }

    }
}
