import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {

    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;

    private Scheduler scheduler;
    private SimulationSetup setup;
    private List<Client> generatedClients = new ArrayList<Client>();

    public SimulationManager() {
        setup = new SimulationSetup(this);
        setup.setVisible(true);
        setup.addStartListener(new StartListener());
    }

    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                timeLimit = Integer.parseInt(setup.getTimeLimit());
                maxProcessingTime = Integer.parseInt(setup.getMaxProcessingTime());
                minProcessingTime = Integer.parseInt(setup.getMinProcessingTime());
                maxArrivalTime = Integer.parseInt(setup.getMaxArrivalTime());
                minArrivalTime = Integer.parseInt(setup.getMinArrivalTime());
                numberOfServers = Integer.parseInt(setup.getNumberOfServers());
                numberOfClients = Integer.parseInt(setup.getNumberOfClients());
                scheduler = new Scheduler(numberOfServers);
                generateNRandomClients();
                showClients();
                setup.showSim();
                setup.frame.setNbOfQueues(numberOfServers);
            } catch (NumberFormatException ex) {
                setup.showError("Verificati datele introduse");
                setup.setVisible(true);
            }
        }
    }

    private void generateNRandomClients() {
        for (int i = 1; i <= this.numberOfClients; i++) {
            Client c = new Client();
            c.setId(i);
            int tSrv = (int) (minProcessingTime + Math.random() * (maxProcessingTime - minProcessingTime));
            int tArv = (int) (minArrivalTime + Math.random() * (maxArrivalTime - minArrivalTime));
            c.settArrival(tArv);
            c.settService(tSrv);
            generatedClients.add(c);
        }
        Collections.sort(generatedClients);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("AICI");
        int currentTime = 0;
        while (currentTime < timeLimit) {
            System.out.println("Am ajuns");
            for (int i = 0; i < generatedClients.size(); i++) {
                Client c = generatedClients.get(i);
                if (c.gettArrival() == currentTime) {
                    scheduler.getMinWaitT().addClient(c);
                    generatedClients.remove(c);
                }
            }
            System.out.println(this.getText());
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getText() {
        String s="";
        s = this.showClients() + "\n";
        for (Server server : scheduler.getServers()) {
            s += server.toString(scheduler.getServers().indexOf(server));
        }
        return s;
    }

    public String showClients() {
        String s = "Clients:";
        for (Client c : generatedClients) {
            s += "(" + c.getId() + ", " + c.gettArrival() + ", " + c.gettService() + "); ";
        }
        return s;
    }
}
