package cs490.sanderlin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class OS {
    private Vector<Process> processTable;

    public void storeProcesses(){ // store all processes
        processTable = new Vector<Process>(); // initialize vector of processes
        try {
            File dataFile = new File("filename.txt"); // initialize file object
            Scanner dataReader = new Scanner(dataFile); // initialize reader
            while (dataReader.hasNextLine()) { // iterates through each line and constructs a process structure for each
                String data = dataReader.nextLine();
                String[] line = data.split(",", 0);
                processTable.add(new Process(line[0], line[1], line[2], line[3]));
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
            System.out.println(processTable.get(0).getId());
            end = true;
            for(int i = 0; i < processTable.size(); i++){
                if(status[i] == 0)
                    end = false;
            }
        }
    }
}
