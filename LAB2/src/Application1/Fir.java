package Application1;
import java.util.Observable;


public class Fir extends Observable implements Runnable {

    int id;

    Window win;

    int processorLoad;

    int c1;

    public int idGetter()
    {
        return id;
    }

    public int c1Getter()
    {
        return c1;
    }

    Fir(int id, Window win, int procLoad) {

        this.id = id;

        this.win = win;

        this.processorLoad = procLoad;

    }

    public void run() {

        int c = 0;

        while (c < 1000) {

            for (int j = 0; j < this.processorLoad; j++) {

                j++;
                j--;

            }
            c++;
            c1 = c;
            this.setChanged();
            this.notifyObservers();
        }
    }
}