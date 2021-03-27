import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUILayout {
    // A list of all the GUI objects that are located in the GUILayout form
    //many of these are just for layout purposes

    //panels
    private JPanel rootPanel; //the root
    private JPanel buttonPanel; //panel to hold start/stop buttons
    private JPanel inputPanel; //panel that holds things related to inputting in the time unit
    private JPanel tablePanel; //panel that holds the queue table
    private JPanel bottomTextPanel; //panel that holds the process table
    private JPanel rightTextPanel; //panel that holds the text areas

    //buttons
    private JButton pauseButton; //pause button
    private JButton startButton; //start button
    private JButton updateButton; //update button

    //labels
    private JLabel systemStatusLabel;
    private JLabel inputLabel;
    private JLabel inputUnitLabel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JLabel Process;
    private JLabel queueLabel;
    private JLabel labelProcess;
    private JLabel throughputLabel;

    //text areas
    public JTextArea textArea1; //text area for cpu 1
    private JTextArea textArea2; //text area for cpu2

    //tables
    private JTable processTable; //actually the table that holds the queue
    private JTable table2; //this is the table that holds the processes

    //text fields
    private JTextField unitTextField; //text field for inputting in a new


    private Object[][] data = {}; //Initialize data to create the default table models

    //creating the default table models
    //this model is used by the queue table
    private DefaultTableModel tableModel = new DefaultTableModel(
            data,
            new String[]{"Process Name", "Service Time"}
    );

    //this model is used by the process table
    private DefaultTableModel tableModel2 = new DefaultTableModel(
            data,
            new String[]{"Process Name", "Arrival Time", "Service Time", "Finish Time", "TAT", "nTAT"}
    );



    //create the tables in the GUI
    public GUILayout() {
        createTable();
        createTable2();
    }
    //functions for creating the tables
    public void createTable() {
        processTable.setModel(tableModel);
    }

    public void createTable2() {
        //Test Data
        Object[][] data =
                {

                };
        table2.setModel(tableModel2);
    }
    //getters
    public JLabel getThroughputLabel()
    {
        return throughputLabel;
    }
    public JTextField getUnitTextField()
    {
        return unitTextField;
    }

    public JButton getUpdateButton()
    {
        return updateButton;
    }
    public JButton getPauseButton() { return pauseButton; }
    public JButton getStartButton() { return startButton; }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextArea getJTextAreaOne() {
        return textArea1;
    }

    public JTextArea getJTextAreaTwo() {
        return textArea2;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public DefaultTableModel getTableModel2() {
        return tableModel2;
    }


}