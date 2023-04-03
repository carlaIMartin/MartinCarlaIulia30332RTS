
class JoinTestThread extends Thread{
    Thread t;
    String n;
    JoinTestThread(String n, Thread t){
        this.setName(n);
        this.t=t;
        this.n=n;
    }
    public void run() {
        System.out.println("Thread "+n+" has entered the run() method");
        try {
            if (t != null) t.join();
            if (n.equals("Thread 1"))
            {
                int sum =0;
                int no = 50000;
                while(no<100000) {
                    if (100000 % no == 0) {
                        sum = sum + no;
                    }
                    no++;
                }
                Main.sum+= sum;
            }else if(n.equals("Thread 2"))
            {
                int sum =0;
                int no = 20000;
                while(no<40000) {
                    if (40000 % no == 0) {
                        sum = sum + no;
                    }
                    no++;
                }
                Main.sum+= sum;
            }

            System.out.println("Thread "+n+" has terminated operation.");
        }
        catch(Exception e){e.printStackTrace();}
    }
}
