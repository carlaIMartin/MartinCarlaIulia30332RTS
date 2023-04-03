
public class ReadThread extends Thread{
    FileService service;
    public ReadThread(FileService service) {
        this.service = service;
    }
    public void run(){
        while (!Main.isStopThreads()){
            try {
                String readMsg;
                synchronized (this.service){
                    readMsg = service.read();
                }
                System.out.println(readMsg);
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

