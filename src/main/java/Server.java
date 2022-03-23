import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Client> q = new ArrayBlockingQueue<Client>(1000);
    private AtomicInteger waitingPeriod=new AtomicInteger();

    public Server(){
        waitingPeriod.set(0);
    }

    public BlockingQueue<Client> getQ() {
        return q;
    }

    public void setQ(BlockingQueue<Client> q) {
        this.q = q;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void addClient(Client newClient){
        try {
            q.put(newClient);
            waitingPeriod.addAndGet(newClient.gettService());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String toString(int i){
        String s="Queue"+String.valueOf(i+1)+": ";
        for (Client c:q) {
            s+=c.toString();
        }
        s+="\n";
        return s;
    }

    @Override
    public void run() {
        while(true){
            try {
                if(!q.isEmpty()) {
                    if (q.peek().gettService() > 1) {
                        waitingPeriod.decrementAndGet();
                        q.peek().settService(q.peek().gettService() - 1);
                    } else{
                        q.take();
                        waitingPeriod.decrementAndGet();
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
