package cs490.sanderlin;

import javax.swing.*;

public class ShellLayout {
    //for building the table later
    Object[][] data = {};
    String[] columnNames = {"Process Name","Service Name"};

    //all the buttons for the forms **need to be updated with better names
    private JButton startSystemButton;
    private JButton pauseSystemButton;
    private JTable table1 = new JTable(data, columnNames);
    private JTextArea textArea;
    private JTextField typeNumberHereTextField;
    private JPanel panelBackground;
    private JTextPane textAreaTextPane;

    //initialize the GUI panel in order to display it
    public static void main(String[] args) {

        JFrame frame = new JFrame("ShellLayout");
        frame.setContentPane(new ShellLayout().panelBackground);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

