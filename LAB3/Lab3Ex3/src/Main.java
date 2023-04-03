
public class Main {
    public static int sum = 0;
    public static void main(String[] args){
        JoinTestThread w1 = new JoinTestThread("Thread 1",null);
        JoinTestThread w2 = new JoinTestThread("Thread 2",w1);
        w1.start();
        w2.start();
        try {
            w1.join();
            w2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The final sum is: " + Main.sum);
    }
}