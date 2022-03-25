import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers=new ArrayList<>();
    private int maxNoServers;

    private List<Thread> threads=new ArrayList<>();

    public Scheduler(int maxNoServers){
        this.maxNoServers=maxNoServers;

        for(int i=0;i<maxNoServers;i++)
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

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
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
}
