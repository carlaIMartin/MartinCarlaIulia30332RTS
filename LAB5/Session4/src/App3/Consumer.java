package App3;

public class Consumer implements Runnable {

    private Numbers numbers;
    private boolean lockingEnabled;

    public Consumer(Numbers numbers, boolean lockingEnabled) {
        this.numbers = numbers;
        this.lockingEnabled = lockingEnabled;
    }


    public void run() {
        try {
            while (true) {
                if (lockingEnabled) {
                    numbers.lock.lock();
                }
                int number = numbers.removeNumber();
                if (lockingEnabled) {
                    numbers.lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + " consumed " + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
