import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class OS implements Runnable{
    private Vector<Process> processTable;
    String filename = "C:/Users/Carolyn/IdeaProjects/Team12_Project_490/src/file.txt";
    GUILayout ui = new GUILayout();
    JTextArea area1 = ui.getJTextAreaOne();
    JTextArea area2 = ui.getJTextAreaTwo();
    DefaultTableModel model = ui.getTableModel();
    DefaultTableModel model2 = ui.getTableModel2();

    public void storeProcesses(){ // store all processes
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

    public void runProcesses(int timeUnit){
        int time = 0;
        int timeRemaining = 0;
        int selection = 0;
        boolean end = false;
        boolean processing = false;
        int status[] = new int[processTable.size()];
        for(int i = 0; i < processTable.size(); i++){
            status[i] = 0;
            System.out.println(processTable.get(i).getId());
        }
        while(!end){
            if(!processing){
                for(int i = 0; i < processTable.size(); i++){
                    if(time >= processTable.get(i).getArrivalTime() && status[i]!=1){
                        selection = i;
                        timeRemaining = processTable.get(i).getServiceTime();
                        processing = true;
                        break;
                    }
                }

            }
            else{
                timeRemaining = timeRemaining - timeUnit;
                if(timeRemaining <= 0) {
                    status[selection] = 1;
                    processing = false;
                }
            }
            time = time + timeUnit;
            System.out.println(time);

            end = true;
            for(int i = 0; i < processTable.size(); i++){
                if(status[i] == 0)
                    end = false;
            }
        }
    }

    public void run()
    {
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(processRunner).start();
    }
    Runnable processRunner = new Runnable()
    {
        int timeUnit = 1000;
        int i = 0;
        public void run()
        {
            try
            {
                while(true)
                {
                    storeProcesses();
                    int time = 0;
                    int timeRemaining = 0;
                    int selection = 0;
                    boolean end = false;
                    boolean processing = false;
                    int status[] = new int[processTable.size()];
                    for(int i = 0; i < processTable.size(); i++){
                        status[i] = 0;
                        System.out.println(processTable.get(i).getId());
                        String textID = processTable.get(i).getId();
                        model.insertRow(model.getRowCount(), new Object[]{processTable.get(i).getId(), processTable.get(i).getArrivalTime(), processTable.get(i).getServiceTime(),processTable.get(i).getPriority()});
                    }
                    while(!end){
                        if(!processing) {
                            for (int i = 0; i < processTable.size(); i++) {
                                if (time >= processTable.get(i).getArrivalTime() && status[i] != 1) {
                                    selection = i;
                                    timeRemaining = processTable.get(i).getServiceTime();
                                    processing = true;
                                    model2.insertRow(model2.getRowCount(), new Object[]{processTable.get(i).getId(), processTable.get(i).getArrivalTime(), processTable.get(i).getServiceTime(), processTable.get(i).getPriority()});
                                    break;
                                }
                            }
                        }
                        else{
                            timeRemaining = timeRemaining - timeUnit;
                            if(timeRemaining <= 0) {
                                status[selection] = 1;
                                processing = false;
                            }
                        }
                        time = time + timeUnit;
                        System.out.println(time);

                        end = true;
                        for(int i = 0; i < processTable.size(); i++){
                            if(status[i] == 0)
                                end = false;
                        }

                        area2.append("Hello, This is a thread!" + i +"\n");

                        area1.append("Hello, This is a thread" + i + "\n");
                        i++;
                        if (i> processTable.size())
                        {
                        }
                        Thread.sleep(timeUnit);
                    }
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    };
}