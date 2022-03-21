import java.util.concurrent.BlockingQueue;

public class Producator implements Runnable{
    BlockingQueue<Client> q;

    public Producator(BlockingQueue q){
        this.q=q;
    }

    @Override
    public void run() {
        for(int i=1;i<10;i++){
            Client c=new Client();
            c.setId(i);
            int t1= (int) (1+Math.random()*4);
            int t2=(int) (1+Math.random()*20);
            c.settArrival(t1);
            c.settService(t2);

            try {
                Thread.sleep(100);
                q.put(c);
                System.out.println("am pus "+c.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
