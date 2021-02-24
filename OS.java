import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class OS {
    public static void main(String[] args){
        Vector<Process> processTable = new Vector<Process>();
        try {
            File dataFile = new File("filename.txt");
            Scanner dataReader = new Scanner(dataFile);
            while (dataReader.hasNextLine()) {
                String data = dataReader.nextLine();
                String[] line = data.split(",", 0);
                processTable.add(new Process(line[0], line[1], line[2], line[3]));
                System.out.println(data);
            }
            dataReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
