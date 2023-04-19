package App4;

public class ExecutionThread extends Thread {

    Integer monitor1;
    Integer monitor2;

    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(Integer monitor1, Integer monitor2, int sleep_min, int sleep_max, int activity_min, int activity_max) {

        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;

    }

    public void run() {
        System.out.println(this.getName() +" State 1");
        try {

            Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

        } catch (InterruptedException e) {

              e.printStackTrace();
        }

        System.out.println(this.getName() +" State 2");
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
        System.out.println(this.getName() +" State 3");

    }


}

class ExecutionThread2 extends Thread {

    Integer monitor;
    Thread t;
    int  activity_min, activity_max;

    public ExecutionThread2(Integer monitor,  int activity_min, int activity_max, Thread t) {

        this.monitor = monitor;
        this.activity_min = activity_min;
        this.activity_max = activity_max;

    }

    public void run()
    {
        System.out.println(this.getName() +" State 1");

        synchronized (monitor) {

            try {

                monitor.wait();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

            System.out.println(this.getName() +" State 2");
            int k = (int) Math.round(Math.random()*(activity_max- activity_min) + activity_min);

            for (int i = 0; i < k * 100000; i++) {

                i++;
                i--;

            }

        System.out.println(this.getName() +" State 3");

        if(this.t!=null){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }
    }

class  Main

{
    public static void main(String[] args) {


        Integer monitor1 = 0;
        Integer monitor2 = 1;
        ExecutionThread t1 = new ExecutionThread(monitor1, monitor2, 7, 7, 2, 3);
        t1.start();
        new ExecutionThread2(monitor1, 3, 5, t1).start();
        new ExecutionThread2(monitor2, 4, 6, t1).start();

    }
}

sdfsdf

