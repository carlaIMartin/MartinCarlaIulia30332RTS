package L4App2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThread extends Thread {

    Integer[] monitor;
    int[] activity1, activity2;
    int sleep;
    ReentrantLock lock1, lock2;

    public ExecutionThread( int sleep, int[] activity1, int[] activity2, ReentrantLock lock1, ReentrantLock lock2) {

        this.monitor = monitor;
        this.sleep = sleep;
        this.activity1 = activity1;
        this.activity2 = activity2;
        this.lock1 = lock1;
        this.lock2 = lock2;

    }

    public void run() {
        System.out.println(this.getName() + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activity1[1] - activity1[0]) + activity1[1]);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }

        try {
            if (lock1.tryLock()) {

                        //lock1.lock();
                    try {

                        System.out.println(this.getName() + " - STATE 2");
                        k = (int) Math.round(Math.random() * (activity2[1] - activity2[0]) + activity2[1]);
                        for (int i = 0; i < k * 100000; i++) {
                            i++;
                            i--;
                        }


                        if (lock2.tryLock()) {
                            try {
                                System.out.println(this.getName() + " - STATE 3");
                                Thread.sleep(sleep);
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }


            }
        } catch (Exception e) {

        }

      try{
            if (lock2.tryLock()) {
                //lock2.lock();
                try {
                    System.out.println(this.getName() + " - STATE 2");
                    k = (int) Math.round(Math.random() * (activity2[1] - activity2[0]) + activity2[1]);
                    for (int i = 0; i < k * 100000; i++) {
                        i++;
                        i--;
                    }


                    try {
                        if (lock1.tryLock(sleep, TimeUnit.SECONDS)) {
                            try {
                                System.out.println(this.getName() + " - STATE 3");
                                Thread.sleep(sleep);
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                } finally {
                    lock2.unlock();
                }



            }
        } catch (Exception e) {
        }
        System.out.println(this.getName() + " - STATE 4");
    }

}



class Main {

    public static void main(String[] args) {


        int[] activity1 = {1, 3};
        int[] activity2 = {2, 4};
        int sleep = 100;

        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        ExecutionThread thread1 = new ExecutionThread( sleep, activity1, activity2, lock1, lock2);
        ExecutionThread thread2 = new ExecutionThread( sleep, activity1, activity2, lock2, lock1);

        thread1.start();
        thread2.start();

    }

}