import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class OS {
    public void storeProcesses(){ // store all processes
        Vector<Process> processTable = new Vector<Process>(); // initialize vector of processes
        try {
            File dataFile = new File("filename.txt"); // initialize file object
            Scanner dataReader = new Scanner(dataFile); // initialize reader
            while (dataReader.hasNextLine()) { // iterates through each line and contructs a process structure for each
                String data = dataReader.nextLine();
                String[] line = data.split(",", 0);
                processTable.add(new Process(line[0], line[1], line[2], line[3]));
                System.out.println(data);
            }
            dataReader.close();
        } catch (FileNotFoundException e) { // notifies the user if the file can't be found
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
