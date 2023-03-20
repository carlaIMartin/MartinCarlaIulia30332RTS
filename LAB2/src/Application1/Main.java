package Application1;
import java.util.Observable;

import Application1.Fir;

public class Main {

     static final int noOfThreads = 6;

    static final int processorLoad = 1000000;

    public static void main(String args[]) {

        Window win = new Window(noOfThreads);

        for (int i = 0; i < noOfThreads; i++) {

            Fir f = new Fir(i, win, processorLoad);
            f.addObserver(win);

           new Thread(f).start();





        }
    }
}
