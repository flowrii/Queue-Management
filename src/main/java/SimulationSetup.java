import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class SimulationSetup extends JFrame {

    private JTextField timeLimit = new JTextField(5);
    private JTextField maxProcessingTime = new JTextField(10);
    private JTextField minProcessingTime = new JTextField(10);
    private JTextField maxArrivalTime = new JTextField(10);
    private JTextField minArrivalTime = new JTextField(10);
    private JTextField numberOfServers = new JTextField(10);
    private JTextField numberOfClients = new JTextField(10);

    private JButton start = new JButton("Start");

    SimulationManager manager;

    SimulationFrame frame=new SimulationFrame(manager);

    SimulationSetup(SimulationManager manager) {

        this.manager=manager;
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        Box box1 = Box.createHorizontalBox();
        box1.add(new JLabel("time limit         "));
        box1.add(timeLimit);
        Box box2 = Box.createHorizontalBox();
        box2.add(new JLabel("max processing time"));
        box2.add(maxProcessingTime);
        Box box3 = Box.createHorizontalBox();
        box3.add(new JLabel("min processing time  "));
        box3.add(minProcessingTime);
        Box box4 = Box.createHorizontalBox();
        box4.add(new JLabel("max arrival time  "));
        box4.add(maxArrivalTime);
        Box box5 = Box.createHorizontalBox();
        box5.add(new JLabel("min arrival time  "));
        box5.add(minArrivalTime);
        Box box6 = Box.createHorizontalBox();
        box6.add(new JLabel("number of servers  "));
        box6.add(numberOfServers);
        Box box7 = Box.createHorizontalBox();
        box7.add(new JLabel("number of clients  "));
        box7.add(numberOfClients);
        Box box8 = Box.createVerticalBox();
        box8.add(box1);
        box8.add(box2);
        box8.add(box3);
        box8.add(box4);
        box8.add(box5);
        box8.add(box6);
        box8.add(box7);
        box8.add(start);
        content.add(box8);

        this.setContentPane(content);
        this.pack();

        this.setTitle("SimulationSetup");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(SimulationFrame.EXIT_ON_CLOSE);

    }
    void showSim(){
        setVisible(false);
        frame.setVisible(true);
    }

    String getTimeLimit() {
        return timeLimit.getText();
    }

    String getMaxProcessingTime() {
        return maxProcessingTime.getText();
    }

    String getMinProcessingTime() {
        return minProcessingTime.getText();
    }

    String getMaxArrivalTime() {
        return maxArrivalTime.getText();
    }

    String getMinArrivalTime() {
        return minArrivalTime.getText();
    }

    String getNumberOfServers() {
        return numberOfServers.getText();
    }

    String getNumberOfClients() {
        return numberOfClients.getText();
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    void addStartListener(ActionListener cal) {
        start.addActionListener(cal);
    }
}

