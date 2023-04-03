
public class Main {
    private static boolean stopThreads = false;
    public static void main(String[] args){
        FileService service = new FileService("messages.txt");
        ReadThread reader = new ReadThread(service);
        WriteThread writer = new WriteThread(service);
        reader.start();
        writer.start();
    }
    public static boolean isStopThreads(){
        return stopThreads;
    }
}
