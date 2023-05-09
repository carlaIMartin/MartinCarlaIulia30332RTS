package App3;

import java.util.Random;

public class Producer implements Runnable {
    private Numbers numbers;
    private Random random;

    public Producer(Numbers numberBuffer) {
        this.numbers = numberBuffer;
        this.random = new Random();

    }


    public void run() {
        try {
            while (true) {
                int number = random.nextInt(100);
                numbers.addNumber(number);
                System.out.println("Producer produced " +number);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Main {
    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        Producer producer = new Producer(numbers);
        Consumer consumer1 = new Consumer(numbers, false);
        Consumer consumer2 = new Consumer(numbers, false);
        Consumer consumer3 = new Consumer(numbers, false);

        Thread producerT = new Thread(producer);
        Thread consumerT1 = new Thread(consumer1);
        Thread consumerT2 = new Thread(consumer2);
        Thread consumerT3 = new Thread(consumer3);

        producerT.start();
        consumerT1.start();
        consumerT2.start();
        consumerT3.start();
    }
}
