public class WriteThread extends Thread{
    FileService service;
    public WriteThread(FileService service) {
        this.service = service;
    }
    public void run(){
        while(!Main.isStopThreads()){
            String msg=
                    String.valueOf(Math.round(Math.random()*100));
            synchronized (this.service) {
                service.write(msg);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
