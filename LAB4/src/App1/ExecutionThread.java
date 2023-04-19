package App1;

//app1
public class ExecutionThread extends Thread {

    Integer [] monitor;

    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(Integer[] monitor, int sleep_min, int sleep_max, int activity_min, int activity_max) {

        this.monitor = monitor;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;

    }

    public void run()
    {
        System.out.println(this.getName() +"State 1");

        if(this.getName().equals("Thread-0") || this.getName().equals("Thread-2"))
        {
            synchronized(monitor[0])
            {
                System.out.println(this.getName() +"State 2");
                int k = (int) Math.round(Math.random()*(activity_max- activity_min) + activity_min);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                try {

                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min)+ sleep_min) * 500);

                } catch (InterruptedException e) {

                      e.printStackTrace();
                }


            }


        }


        if(this.getName().equals("Thread-1")) {
            synchronized (monitor[0]) {
                synchronized (monitor[1]) {
                System.out.println(this.getName() +"State 2");
                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);

                for (int i = 0; i < k * 100000; i++) {

                    i++;
                    i--;

                }

                try {

                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }
        }
        }
        System.out.println(this.getName() + " State 3");

    }
}

 class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Integer[] monitor = {0, 1};
        Integer[] monitor1 = {0};
        Integer[] monitor2 = {1};

        new ExecutionThread(monitor, 4, 4, 2, 4).start();
        new ExecutionThread(monitor, 3, 3, 3, 6).start();
        new ExecutionThread(monitor, 5, 5, 2, 5).start();

    }

}