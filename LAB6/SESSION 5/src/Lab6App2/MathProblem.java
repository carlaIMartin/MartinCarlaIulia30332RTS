package Lab6App2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MathProblem {

    static final int MIN_VALUE = -10;
    static final int MAX_VALUE = 10;
    static final int NUM_THREADS = 3;

    static int iterationCount = 0;
    static int sum = 0;

    private static final CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS, new Runnable() {
        @Override
        public void run() {
            iterationCount++;
            if (sum == 0) {
                System.out.println("Number of iterations performed by each thread: " + iterationCount);

            }
            sum = 0;
        }
    });
    private static class WriteIterate implements Runnable {

        private final BufferedWriter writer;
        private final Random random = new Random();

        public  WriteIterate(BufferedWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            while (true) {
                int value = random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
                sum += value;
                try {
                    barrier.await();
                    writer.write(String.format("%d %d\n", iterationCount, sum));
                    writer.flush();
                } catch (InterruptedException | BrokenBarrierException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException, IOException {

        // create a BufferedWriter to write results to a file
        BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));


        Thread t1 = new Thread(new  WriteIterate(writer));
        Thread t2 = new Thread(new WriteIterate(writer));
        Thread t3 = new Thread(new  WriteIterate(writer));


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();


        writer.close();
    }


}