import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.lang.Thread;
import java.text.DecimalFormat;

//The powerhouse of the project. This

public class OS implements Runnable {
    //----------------------------------------Variables----------------------------------------------
    //--------------FOR: Storing Processes
    private Vector<Process> processTable; //create a vector of processes
    private GlobalQueue queue = new GlobalQueue(); //create a global queue
    private Semaphore sem = new Semaphore (1, true);
    private int totalProcess = 0; //get the total number of processes added to a queue
    private static final Object lock = new Object();
    private boolean flag = false;


    //---------------FOR: Updating the GUI
    private GUILayout ui = new GUILayout(); // create a GUILayout object to update the GUI
    private JTextArea area1 = ui.getJTextAreaOne(); // to update ext Box One
    private JTextArea area2 = ui.getJTextAreaTwo(); // to update text Box Two
    private DefaultTableModel model = ui.getTableModel(); // to update table 1
    private DefaultTableModel model2 = ui.getTableModel2(); // to update table 2
    private JButton button = ui.getUpdateButton(); //create a button to put a listener on it
    private DecimalFormat df = new DecimalFormat("#0.###########"); // to format the current throughput
    private JButton pauseButton = ui.getPauseButton();
    private JButton startButton = ui.getStartButton();

    //the button listener to update the time unit
    public OS() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeUnit = Integer.parseInt((ui.getUnitTextField().getText())); //when the button is pushed
            }
        });
    }

    public void Pause() {
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock){
                    /*while (!flag){
                        System.out.println("Pause button is pressed.");
                    }*/
                    /*try {
                        one.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }*/
                    System.out.println("Pause button is pressed.");
                }
                /*
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }*/ //testing if threads are responding
            }
        });
    }

    public void Start() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CPUOne.notify(); //when the button is pushed
                //CPUTwo.notify(); //when the button is pushed
                synchronized (lock){
                    //CPUOne.notify();
                    System.out.println("Start button is pressed.");
                }
            }
        });
    }

    //---------------FOR: Users to Update
    public String filename; // a placeholder for a file (C://Users//Carolyn//IdeaProjects//Team12_Project_490//src//file.txt)
    private int timeUnit = Integer.parseInt(ui.getUnitTextField().getText()); // a time unit that can be updated by the user


    //----------------------------------------Functions ----------------------------------------------
    //---------------FOR: Initializing the GUI + Starting Threads
    //A function for setting up the GUI so it can run when we start the threads
    private void GUIPack() {
        //Pack the GUI in preparation for running the processes
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //initialize the GUI, add process to the global queue, and start the threads
    public void run() {
        GUIPack();
        addToQueue(); //add the processes  the queue
        Thread thread1 = new Thread(CPUOne);
        Thread thread2 = new Thread(CPUTwo);
        thread1.start(); //start thread one
        thread2.start(); //start thread two

        //pausing and starting the threads
        Pause();
        Start();
    }
    //---------------FOR: Storing Processes

    //set the file name (used by the EnterFile class when the launch button is pressed
    public void setFilename(String filepath)
    {
        filename = filepath;
    }

    //For storing processes in a FIFO global queue
    public void addToQueue() {
        try {
            File dataFile = new File(filename); // initialize file object
            Scanner dataReader = new Scanner(dataFile); // initialize reader
            while (dataReader.hasNextLine()) { // iterates through each line and constructs a process structure for each
                String data = dataReader.nextLine(); //get the next line
                String[] line = data.split(",", 0); //split along commas
                Process newProcess = new Process(line[0], line[1], line[2], line[3]);
                queue.add(newProcess); //add to queue
                totalProcess++; //add a number to the total process count
                System.out.println(newProcess.getId() + " " + newProcess.getPriority() + " " + newProcess.getServiceTime() + " " + newProcess.getArrivalTime()); //print to console for testing
            }
            dataReader.close(); // close out
        } catch (FileNotFoundException e) { // notifies the user if the file can't be found
            System.out.println("File not found.");
            e.printStackTrace();
        }
        //add things to the queue table by iterating through the queue
        UpdateQueueTable();
    }
    //-------------------FOR: Defining the Threads
    //This is CPU One
    Runnable CPUOne = new Runnable() {
        int i = 0;

        public void run() {
            try {
                while (true) {
                    int status[] = new int[totalProcess]; //tally of the total amount of processes
                    //------------variables
                    int time = 0;
                    int timeRemaining = 0;
                    int selection = 0;
                    double currentThroughput;
                    boolean end = false;
                    boolean processing = false;
                    while (!end) {

                        if (!processing) { //selects a process to run
                            int i = 0;
                            if (!queue.isEmpty()) {
                                Process process = queue.peek();
                                if (time >= process.getArrivalTime()) {
                                    timeRemaining = process.getServiceTime();
                                    processing = true;
                                    selection = i;
                                    area1.setText("CPU One is currently Processing: " + process.getId() + "\n");
                                    i++;
                                }
                            }
                        }
                        if (processing) { //calculates process progress
                            timeRemaining = timeRemaining - timeUnit; //decrements remaining time
                            if (timeRemaining <= 0) { //sets process status to finished and tells the processor to select the next process
                                Process process = queue.poll();
                                area1.setText("CPU One just Processed: " + process.getId() + "\n");
                                model.setRowCount(0);
                                UpdateQueueTable();
                                status[selection] = 1; //updates selected process status to finished

                                process.setFinishTime(time); //sets finish time
                                process.setTAT(time); //sets turnaround time and normalized turnaround time
                                addToProcessTable(process);
                                processing = false; //stops processing
                            }
                        }
                        System.out.println(time);
                        end = true; //sets end to true because it's easier to check if the loop shouldn't end
                        currentThroughput = 0; //resets for incrementation
                        for (int i = 0; i < totalProcess; i++) { //checks if process should end
                            if (!queue.isEmpty()) //program continues processing until all all processes are complete
                                end = false; //end is false if any process is unfinished
                            if (status[i] == 1)
                                currentThroughput++; //increments for each completed process
                        }
                        currentThroughput = currentThroughput / time; //divides number of finished processes by time
                        time = time + timeUnit; //increments time
                        i++;
                        Thread.sleep(timeUnit);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    //This is CPU Two
    Runnable CPUTwo = new Runnable() {
        int i = 0;
        public void run() {
            try {
                while (true) {
                    int status[] = new int[totalProcess]; //used to see if a process is finished or not
                    //------------variables
                    int time = 0;
                    int timeRemaining = 0;
                    int selection = 0;
                    double currentThroughput;
                    boolean end = false;
                    boolean processing = false;
                    while (!end) {
                        if (!processing) { //selects a process to run
                            int i = 0;
                            if (!queue.isEmpty()) {
                                Process process = queue.peek();
                                if (time >= process.getArrivalTime()) {
                                    timeRemaining = process.getServiceTime();
                                    processing = true;
                                    selection = i;
                                    area2.setText("CPU Two is currently Processing: " + process.getId() + "\n");
                                    i++;

                                }
                            }
                        }
                        if (processing) { //calculates process progress
                            timeRemaining = timeRemaining - timeUnit; //decrements remaining time
                            if (timeRemaining <= 0) { //sets process status to finished and tells the processor to select the next process
                                Process process = queue.poll();

                                model.setRowCount(0);
                                area2.setText("CPU Two just Processed: " + process.getId() + "\n");
                                UpdateQueueTable();

                                status[selection] = 1; //updates selected process status to finished

                                process.setFinishTime(time); //sets finish time
                                process.setTAT(time); //sets turnaround time and normalized turnaround time
                                addToProcessTable(process);
                                processing = false; //stops processing
                            }
                        }
                        System.out.println(time);
                        end = true; //sets end to true because it's easier to check if the loop shouldn't end
                        currentThroughput = 0; //resets for incrementation
                        for (int i = 0; i < totalProcess; i++) { //checks if process should end
                            if (!queue.isEmpty()) //program continues processing until all all processes are complete
                                end = false; //end is false if any process is unfinished
                            if (status[i] == 1)
                                currentThroughput++; //increments for each completed process
                        }
                        currentThroughput = currentThroughput / time; //divides number of finished processes by time
                        JLabel ctp = ui.getThroughputLabel();
                        ctp.setText("Current Throughput = " + df.format(currentThroughput) +"ms"); //converts to decimal format and updates jLabel
                        time = time + timeUnit; //increments time
                        i++;
                        Thread.sleep(timeUnit);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //for updating the Queue table with what is currently in the queue.
    public void UpdateQueueTable()
    {
        for(Process process: queue.get()) //get the actual queue inside the global queue class and iterate through them
        {
            model.insertRow(model.getRowCount(), new Object[]{process.getId(), process.getServiceTime()});// add to the queue table
        }
    }
    //for updating the process table
    public void addToProcessTable(Process process)
    {
        model2.insertRow(model2.getRowCount(), new Object[]{process.getId(), process.getArrivalTime(), process.getServiceTime(), process.getFinishTime(),process.getTAT(),process.getnTAT()});
    }

    //pausing the threads
   /*public void pauseThreads() throws InterruptedException {
        /*
        ActionListener pauseButtonListener = new OS.PauseButtonListener();
        pauseButton.addActionListener(pauseButtonListener);
       synchronized (CPUOne){
           CPUOne.wait();
       }
    }*/


}