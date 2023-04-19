package App2;

public class ExecutionThread extends Thread {

    Integer[] monitor;

    int sleep_min, sleep_max;
    int[] activity1, activity2;

    public ExecutionThread(Integer[] monitor, int sleep_min, int sleep_max, int [] activity1, int[] activity2 ) {

        this.monitor = monitor;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1 = activity1;
        this.activity2= activity2;

    }

    public void run()
    {

        System.out.println(this.getName() +" State 1");
        int k = (int) Math.round(Math.random()*(activity1[1]- activity1[0]) + activity1[0]);

        for (int i = 0; i < k * 100000; i++) {

            i++;
            i--;

        }


        if (this.getName().equals("Thread-0"))
        {
            synchronized(monitor[0]) {
                System.out.println(this.getName() +" State 2");
                k = (int) Math.round(Math.random()*(activity2[1]- activity2[0]) + activity2[0]);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                synchronized(monitor[1])
                {
                    System.out.println(this.getName() +" State 3");
                    try {

                        Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

                    } catch (InterruptedException e) {

                          e.printStackTrace();
                    }

                }

            }
        }




        if (this.getName().equals("Thread-1"))
        {
            synchronized(monitor[1]) {
                System.out.println(this.getName() +" State 2");
                k = (int) Math.round(Math.random()*(activity2[1]- activity2[0]) + activity2[0]);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                synchronized(monitor[0])
                {
                    System.out.println(this.getName() +" State 3");
                    try {

                        Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

                    } catch (InterruptedException e) {

                          e.printStackTrace();
                    }

                }
            }
        }

        System.out.println(this.getName() +" State 4");
    }
}


class Main
{
    public static void main(String[] args) {
        Integer[] monitor = {0, 1};
        Integer[] monitor1 = {0};
        Integer[] monitor2 = {1};
        int[] activity1Thread1 = {2, 4};
        int[] activity2Thread1 = {4, 6};

        int[] activity1Thread2 = {3, 5};
        int[] activity2Thread2 = {5, 7};
        ExecutionThread thread1 = new ExecutionThread(monitor, 4, 6, activity1Thread1, activity2Thread1);
        ExecutionThread thread2 = new ExecutionThread(monitor, 1, 3, activity1Thread2, activity2Thread2);
        thread1.start();
        thread2.start();
    }
}
//deadlock reached, by fixing the delay and per example start the second monitor from the same transition as the first
