import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Server> servers=new ArrayList<>();

    private final List<Thread> threads=new ArrayList<>();

    public Scheduler(int nbServers){

        for(int i=0;i<nbServers;i++)
        {
            Server s=new Server();
            servers.add(s);
            Thread t=new Thread(s);
            threads.add(t);
            t.start();
        }
    }

    public Server getMinWaitT(){
        int mini=servers.get(0).getWaitingPeriod().get();
        Server minS=servers.get(0);
        for (Server s:servers) {
            if(s.getWaitingPeriod().get()<mini){
                mini=s.getWaitingPeriod().get();
                minS=s;
            }
        }
        return minS;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void interruptThreads(){
        for (Thread t:threads) {
            t.interrupt();
        }
    }

    public boolean checkIfThereAreClients(){
        for (Server s:servers) {
            if(!s.getQ().isEmpty())
                return true;
        }
        return false;
    }

    public int unservedClientsInQ(){
        for (Server s:servers) {
            if(s.getQ().size()>1)
                return s.getQ().size()-1;
        }
        return 0;
    }

    public int howManyClients(){
        int ct=0;
        for (Server s:servers) {
            if(!s.getQ().isEmpty())
                ct+= s.getQ().size();
        }
        return ct;
    }
}
