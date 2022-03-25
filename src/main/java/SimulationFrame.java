import java.awt.*;
import javax.swing.*;

public class SimulationFrame extends JFrame{

    private final JTextArea vw = new JTextArea(30,133);

    public void setVwText(String string) {
        this.vw.setText(string);
    }

    SimulationFrame()
    {
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        this.setMinimumSize(new Dimension(1500,550));

        vw.setLineWrap(true);
        vw.setWrapStyleWord(true);
        vw.setMaximumSize(new Dimension(400,500));

        JScrollPane scroll = new JScrollPane (vw,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //content.add(vw);
        content.add(scroll);

        this.setContentPane(content);
        this.pack();

        this.setTitle("SimulationFrame");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
