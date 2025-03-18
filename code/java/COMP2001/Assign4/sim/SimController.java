package sim;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI control panel for the simulator
 *
 * @author Dewan Mukto
 * @version 29 Nov 2022
 */
public class SimController
{
    private Simulator simulator;
    private Timer timer;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem quit;
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private JLabel isSimRunning;
    private JButton run;
    private JButton stop;
    private JButton step;
    private JButton slower;
    private JTextField timerDelay;
    private JButton faster;
    private JButton runTo;
    private JTextField runToLimit;
    private JButton reset;
    private JLabel simStatus;
    private int stepCount;

    /**
     * Constructor for objects of class SimController
     */
    public SimController()
    {
        timer = new Timer(100, e -> heartBeat());
        makeFrame();
        simulator = new Simulator();
        timer.start();
        stepCount = 0;
    }

    private void heartBeat(){
        if(isSimRunning.getText().equals("Sim Running")){
            simulator.simulateOneStep();
            simStatus.setText("Step: ["+simulator.getStep()+"] "+simulator.getDetails());
        }
    }

    /**
     * Launches the controller GUI
     */
    private void makeFrame(){
        frame = new JFrame("Sim Control");
        JPanel contentPane = (JPanel) frame.getContentPane();
        makeMenuBar(frame);

        contentPane.setLayout(new FlowLayout());

        JPanel SimRunning = new JPanel(new BorderLayout());
        JPanel RunStopStep = new JPanel(new BorderLayout());
        JPanel SlowerDelayFaster = new JPanel(new BorderLayout());
        JPanel RunLimitReset = new JPanel(new BorderLayout());
        JPanel StatusBar = new JPanel(new BorderLayout());

        isSimRunning = new JLabel("Sim Not Running");
        run = new JButton("Run");
        run.addActionListener(action -> runSim());
        stop = new JButton("Stop");
        stop.addActionListener(action -> stopSim());
        step = new JButton("Step");
        step.addActionListener(action -> stepSim());
        slower = new JButton("Slower");
        slower.addActionListener(action -> slowerSim());
        timerDelay = new JTextField(Integer.toString(timer.getDelay()));
        timerDelay.addActionListener(action -> setDelay());
        faster = new JButton("Faster");
        faster.addActionListener(action -> fasterSim());
        runTo = new JButton("Run To:");
        runTo.addActionListener(action -> runTo());
        runToLimit = new JTextField(Integer.toString(0));
        runToLimit.addActionListener(action -> changeLimit());
        reset = new JButton("Reset");
        reset.addActionListener(action -> resetSim());
        simStatus = new JLabel("Status Bar");

        SimRunning.add(isSimRunning, BorderLayout.NORTH);
        RunStopStep.add(run, BorderLayout.WEST);
        RunStopStep.add(stop, BorderLayout.CENTER);
        RunStopStep.add(step, BorderLayout.EAST);
        SlowerDelayFaster.add(slower, BorderLayout.WEST);
        SlowerDelayFaster.add(timerDelay, BorderLayout.CENTER);
        SlowerDelayFaster.add(faster, BorderLayout.EAST);
        RunLimitReset.add(runTo, BorderLayout.WEST);
        RunLimitReset.add(runToLimit, BorderLayout.CENTER);
        RunLimitReset.add(reset, BorderLayout.EAST);
        StatusBar.add(simStatus, BorderLayout.SOUTH);

        JPanel flow = new JPanel();
        flow.add(SimRunning);
        flow.add(RunStopStep);
        flow.add(SlowerDelayFaster);
        flow.add(RunLimitReset);
        flow.add(StatusBar);
        contentPane.add(flow);

        frame.pack();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }

    private void makeMenuBar(JFrame frame){
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu menu;
        JMenuItem item;

        menu = new JMenu("File");
        menubar.add(menu);

        item = new JMenuItem("Load Settings");
        item.addActionListener(action -> openFile());
        menu.add(item);

        item = new JMenuItem("Save Settings");
        item.addActionListener(action -> saveFile());
        menu.add(item);

        menu.addSeparator();
        item = new JMenuItem("Quit");
        item.addActionListener(action -> quit());
        menu.add(item);
    }

    public void actionPerformed(ActionEvent event){
        System.out.println("An action event was triggered!");
        return;
    }

    public void openFile(){
        int ioreturn = fileChooser.showOpenDialog(frame);
        if(ioreturn != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled selection
        } else if(ioreturn == JFileChooser.APPROVE_OPTION){
            JOptionPane notyet = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Not implemented", "Load Settings", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveFile(){
        int ioreturn = fileChooser.showSaveDialog(frame);
        if(ioreturn != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled selection
        } else if(ioreturn == JFileChooser.APPROVE_OPTION){
            JOptionPane notyet = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Not implemented", "Save Settings", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void quit(){
        System.out.println("The Quit menu button was clicked!");
        timer.stop();
        simulator.endSimulation();
        frame.setVisible(false);
        frame.dispose();
    }

    public void runSim(){
        System.out.println("The Run button was clicked!");
        isSimRunning.setText("Sim Running");
    }

    public void stopSim(){
        System.out.println("The Stop button was clicked!");
        isSimRunning.setText("Sim Not Running");
    }

    public void stepSim(){
        simulator.simulateOneStep();
        simStatus.setText("Step: ["+simulator.getStep()+"] "+simulator.getDetails());
        System.out.println("The Step button was clicked!");
    }

    public void slowerSim(){
        timer.setDelay(timer.getDelay()*2);
        timerDelay.setText(Integer.toString(timer.getDelay()));
        System.out.println("The Slower button was clicked!");
    }

    public void setDelay(){
        if(timer.getDelay()>5){
            timer.setDelay(Integer.parseInt(timerDelay.getText()));
        } else {
            timer.setDelay(5);
        }
        System.out.println("The timer delay has been changed to "+timer.getDelay());
    }

    public void fasterSim(){
        if(timer.getDelay()>5){
            timer.setDelay(timer.getDelay()/2);
        } else {
            timer.setDelay(5);
        }
        timerDelay.setText(Integer.toString(timer.getDelay()));
        System.out.println("The Faster button was clicked!");
    }

    public void runTo(){
        simulator.simulate(Integer.parseInt(runToLimit.getText()));
        simStatus.setText("Step: ["+simulator.getStep()+"] "+simulator.getDetails());
        System.out.println("The simulator is now running to "+runToLimit.getText());
    }

    public void changeLimit(){
        runToLimit.setText(runToLimit.getText());
        System.out.println("The run to limit has been changed to "+runToLimit.getText());
    }

    public void resetSim(){
        simulator.reset();
        System.out.println("The simulator has been reset");
    }

    public Simulator getSimulator(){
        return simulator;
    }

    public static void main(){
        SimController simcontrol = new SimController();
    }
}
