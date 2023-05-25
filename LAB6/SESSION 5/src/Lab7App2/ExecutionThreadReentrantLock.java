package Lab7App2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThreadReentrantLock extends Thread {


    ReentrantLock lock1, lock2;

    CyclicBarrier barrier ;

    int sleep_min, sleep_max;
    int [] activity1, activity2;

    public ExecutionThreadReentrantLock(ReentrantLock lock1, CyclicBarrier barrier,  int sleep_min, int sleep_max, int[] activity1) {

        this.barrier = barrier;
        this.lock1 = lock1;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;
        this.activity2 = activity2;

    }

    public void run()
    {
        System.out.println(this.getName() + " - State 1");

        if(lock1.tryLock())
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


        }finally
        {
            lock1.unlock();
        }

        System.out.println(this.getName() + " - State 3");

        try{
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }



    }
}


class ExecutionThreadReentrantLock2 extends Thread {

    Integer monitor;
    ReentrantLock lock1, lock2;

    CyclicBarrier barrier ;

    int sleep_min, sleep_max;
    int [] activity1, activity2;

    public ExecutionThreadReentrantLock2(ReentrantLock lock1, ReentrantLock lock2,  CyclicBarrier barrier,  int sleep_min, int sleep_max, int[] activity1) {

        this.lock2=lock2;
        this.barrier = barrier;
        this.lock1 = lock1;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;
        this.activity2 = activity2;

    }

    public void run()
    {
        System.out.println(this.getName() + " - State 1");

        if(lock1.tryLock() && lock2.tryLock())  //we dont  need trylock
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


            }finally
            {
                lock1.unlock();
                lock2.unlock();
            }

        System.out.println(this.getName() + " - State 3");

        try{
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }



    }
}

class Mainz
{
            public static void main(String[] args) throws BrokenBarrierException, InterruptedException{
                ReentrantLock lock1 = new ReentrantLock();
                ReentrantLock lock2 = new ReentrantLock();

                int[] activity1 = {2, 4};
                int[] activity2 = {3,6};
                int[] activity3 = {2, 5};



                CyclicBarrier barrier = new CyclicBarrier(4);

                while(true)
                {
                    new ExecutionThreadReentrantLock(lock1, barrier, 4, 4, activity1 ).start();
                    new ExecutionThreadReentrantLock(lock2, barrier, 3, 3, activity2 ).start();
                    new ExecutionThreadReentrantLock2(lock1, lock2, barrier,5, 5, activity3 ).start();

                    barrier.await();

                    barrier.reset();

                }

            }
        }



