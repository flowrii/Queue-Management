import java.util.concurrent.BlockingQueue;

public class Consumator implements Runnable {
    BlockingQueue<Client> q;

    public Consumator(BlockingQueue q) {
        this.q = q;
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            try {
                Client c = q.take();
                Thread.sleep(500);
                System.out.println("am scos " + c.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
