import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    //public static void main(String[] args) {
        /*BlockingQueue<Client> q = new ArrayBlockingQueue<Client>(10);

        Producator p = new Producator(q);
        Consumator c = new Consumator(q);

        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();*/
        public static void main(String[] args) {
            SimulationManager gen = new SimulationManager();
            //SimulationSetup setup=new SimulationSetup(gen);
            Thread t = new Thread(gen);
            t.start();
        }
    //}
}
