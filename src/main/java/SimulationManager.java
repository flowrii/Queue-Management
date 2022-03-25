import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
                if(timeLimit<0 || maxProcessingTime<0 || minProcessingTime<0 || maxArrivalTime<0 || minArrivalTime<0 || numberOfServers<=0 ||numberOfClients<0)
                    setup.showError("Valorile introduse trebuie sa fie numere intregi, pozitive!");
                else {
                    if ((minProcessingTime > maxProcessingTime) || (minArrivalTime > maxArrivalTime))
                        setup.showError("Timpii minimi trebuie sa fie mai mici decat timpii maximi!");
                    else {
                        scheduler = new Scheduler(numberOfServers);
                        generateNRandomClients();
                        setup.frame.setNbOfQueues(numberOfServers);
                        setup.showSim();
                    }
                }
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

    public String showClients() {
        String s = "Clients:";
        for (Client c : generatedClients) {
            s += "(" + c.getId() + ", " + c.gettArrival() + ", " + c.gettService() + "); ";
        }
        return s;
    }

    public String getText() {
        String s="";
        s = this.showClients() + "\n\n";
        for (Server server : scheduler.getServers()) {
            s += server.toString(scheduler.getServers().indexOf(server));
        }
        return s;
    }

    public void writeToTxt(String s, File f1) throws IOException {
        FileWriter fileWriter=new FileWriter(f1.getName(),true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(s);
        bw.close();
    }

    public void createOrClearFile(File f1) throws FileNotFoundException {
        if(!f1.exists()){
            try {
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            PrintWriter writer = new PrintWriter(f1);
            writer.print("");
            writer.close();
        }
    }

    public int remainingWaitingTime(Scheduler scheduler){
        int time=0;
        for (Server s:scheduler.getServers()) {
            int ct=0;
            if(!s.getQ().isEmpty()){
                for (Client c:s.getQ()) {
                    if(s.getQ().size()==1)
                        time+=c.gettService();
                    else
                        time+=c.gettService()*(s.getQ().size()-ct);
                    ct++;
                }
            }
        }
        return time;
    }

    public int remainingServiceTime(Scheduler scheduler){
        int time=0;
        for (Server s:scheduler.getServers()) {
            if(!s.getQ().isEmpty()){
                for (Client c:s.getQ()) {
                    time+=c.gettService();
                }
            }
        }
        return time;
    }

    @Override
    public void run() {
        File f1=new File("C:\\Users\\iften\\Desktop\\PT2022_30221_Iftene_Ioan_Florin_Assignment_2\\log.txt");
        try {
            createOrClearFile(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int currentTime = 0, clientsInQueues=0;
        double avgWaitingTime=0, avgServiceTime=0;
        while (currentTime < timeLimit) {
            if (generatedClients.isEmpty() && !scheduler.checkIfThereAreClients())
                break;
            for (int i = 0; i < generatedClients.size(); i++) {
                Client c = generatedClients.get(i);
                if (c.gettArrival() == currentTime) {
                    avgWaitingTime+=scheduler.getMinWaitT().getWaitingPeriod().get()+c.gettService();
                    scheduler.getMinWaitT().addClient(c);
                    avgServiceTime+=c.gettService();
                    clientsInQueues++;
                    generatedClients.remove(c);
                    i--;
                }
            }
            String deScris="Time:"+currentTime+"\n"+this.getText();
            setup.frame.setVwText(deScris);
            try {
                writeToTxt("\n"+deScris, f1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime++;
        }
        if(scheduler.checkIfThereAreClients()){
            avgWaitingTime-=remainingWaitingTime(scheduler);
            avgServiceTime-=remainingServiceTime(scheduler);
        }
        avgWaitingTime/=clientsInQueues*1.0;
        avgServiceTime/=(clientsInQueues-scheduler.unservedClientsInQ())*1.0;
        String deScris="Time:"+currentTime+"\n"+this.getText()+"\n\nAverage waiting time: "+avgWaitingTime+"\nAverage service time: "+avgServiceTime;
        setup.frame.setVwText(deScris);
        try {
            writeToTxt("\n"+deScris, f1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.interrupted(); scheduler.interruptThreads();
    }

}
