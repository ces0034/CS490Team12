import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class OS implements Runnable {


    //----------------------------------------Variables----------------------------------------------

    //--------------FOR: Storing Processes
    private Vector<Process> processTable; //create a vector of processes
    GlobalQueue queue = new GlobalQueue(); //create a global queue
    Semaphore sem = new Semaphore (1, true);
    private int totalProcess = 0; //get the total number of processes added to a queue
    int status[];  //used to see if a process is finished or not
    long startTime;
    long endTime;
    Instant start;
    //---------------FOR: Users to Update
    String filename = "C:/Users/Carolyn/IdeaProjects/Team12_Project_490/src/file.txt"; // a placeholder for a file
    private int timeUnit = 1000; // a time unit that can be updated by the user


    //---------------FOR: Updating the GUI
    GUILayout ui = new GUILayout(); // create a GUILayout object to update the GUI
    JTextArea area1 = ui.getJTextAreaOne(); // to update ext Box One
    JTextArea area2 = ui.getJTextAreaTwo(); // to update text Box Two
    DefaultTableModel model = ui.getTableModel(); // to update table 1
    DefaultTableModel model2 = ui.getTableModel2(); // to update table 2


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
        new Thread(CPUTwo).start();
        //new Thread(CPUOne).start();

    }


    //---------------FOR: Storing Processes

    //For storing processes in a  Vector of Processes
    public void storeProcesses() { // store all processes
        processTable = new Vector<Process>(); // initialize vector of processes
        try {
            File dataFile = new File(filename); // initialize file object
            Scanner dataReader = new Scanner(dataFile); // initialize reader
            while (dataReader.hasNextLine()) { // iterates through each line and constructs a process structure for each
                String data = dataReader.nextLine();
                String[] line = data.split(",", 0);
                processTable.add(new Process(line[0], line[1], line[2], line[3]));
                System.out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3]);
            }
            dataReader.close();
        } catch (FileNotFoundException e) { // notifies the user if the file can't be found
            System.out.println("File not found.");
            e.printStackTrace();
        }
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
       start = Instant.now();
    }


    //-------------------FOR: Defining the Threads

    //This is CPU One
    Runnable CPUOne = new Runnable() {

        int i = 0;

        public void run() {
            while(sem.tryAcquire()) {
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
                                        area1.setText("Currently Processing: " + process.getId() + "\n");
                                        i++;

                                    }
                                }
                            }
                            if (processing) { //calculates process progress

                                timeRemaining = timeRemaining - timeUnit; //decrements remaining time

                                if (timeRemaining <= 0) { //sets process status to finished and tells the processor to select the next process

                                    Process process = queue.poll();
                                    model.setRowCount(0);
                                    UpdateQueueTable();

                                    status[selection] = 1; //updates selected process status to finished
                                    Instant endTime = Instant.now();
                                    Duration timeElapsed = Duration.between(start, endTime);
                                    long toMS = timeElapsed.toMillis();
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
                                else currentThroughput++; //increments for each completed process
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
                sem.release();
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
                                        area2.setText("Currently Processing: " + process.getId() + "\n");
                                        i++;

                                    }
                                }
                            }
                            if (processing) { //calculates process progress

                                timeRemaining = timeRemaining - timeUnit; //decrements remaining time

                                if (timeRemaining <= 0) { //sets process status to finished and tells the processor to select the next process

                                    Process process = queue.poll();
                                    model.setRowCount(0);
                                    UpdateQueueTable();

                                    status[selection] = 1; //updates selected process status to finished
                                    Instant endTime = Instant.now();
                                    Duration timeElapsed = Duration.between(start, endTime);
                                    long toMS = timeElapsed.toMillis();
                                    process.setFinishTime(time); //sets finish time
                                    process.setTAT(time); //sets turnaround time and normalized turnaround time
                                    addToProcessTable(process);
                                    processing = false; //stops processing
                                    sem.release();
                                }
                            }
                            System.out.println(time);
                            end = true; //sets end to true because it's easier to check if the loop shouldn't end
                            currentThroughput = 0; //resets for incrementation
                            for (int i = 0; i < totalProcess; i++) { //checks if process should end
                                if (!queue.isEmpty()) //program continues processing until all all processes are complete
                                    end = false; //end is false if any process is unfinished
                                else currentThroughput++; //increments for each completed process
                            }
                            currentThroughput = currentThroughput / time; //divides number of finished processes by time
                            JLabel ctp = ui.getThroughputLabel();
                            ctp.setText("Current Throughput = " + currentThroughput +"ms");
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

    public void UpdateQueueTable()
    {

        for(Process process: queue.get()) //get the actual queue inside the global queue class and iterate through them
        {
            model.insertRow(model.getRowCount(), new Object[]{process.getId(), process.getServiceTime()});// add to the queue table
        }

    }
    public void addToProcessTable(Process process)
    {
        model2.insertRow(model2.getRowCount(), new Object[]{process.getId(), process.getArrivalTime(), process.getServiceTime(), process.getFinishTime(),process.getTAT(),process.getnTAT()});
    }

    //-----------------------------DEFUNCT CODE (NO LONGER NEEDED) ------------------------------

    //defunct
    public void runProcesses(int timeUnit) {
        int time = 0;
        int timeRemaining = 0;
        int selection = 0;
        boolean end = false;
        boolean processing = false;
        int status[] = new int[processTable.size()];
        for (int i = 0; i < processTable.size(); i++) {
            status[i] = 0;
            System.out.println(processTable.get(i).getId());
        }
        while (!end) {
            if (!processing) {
                for (int i = 0; i < processTable.size(); i++) {
                    if (time >= processTable.get(i).getArrivalTime() && status[i] != 1) {
                        selection = i;
                        timeRemaining = processTable.get(i).getServiceTime();
                        processing = true;
                        break;
                    }
                }

            } else {
                timeRemaining = timeRemaining - timeUnit;
                if (timeRemaining <= 0) {
                    status[selection] = 1;
                    processing = false;
                }
            }
            time = time + timeUnit;
            System.out.println(time);

            end = true;
            for (int i = 0; i < processTable.size(); i++) {
                if (status[i] == 0)
                    end = false;
            }
        }
    }

}