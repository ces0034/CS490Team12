import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUILayout {
    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel tablePanel;
    private JPanel bottomTextPanel;
    private JButton pauseButton;
    private JButton startButton;
    private JLabel systemStatusLabel;
    private JTextField unitTextField;
    private JLabel inputLabel;
    private JLabel inputUnitLabel;
    private JTextPane textPaneBottom;
    private JTable processTable;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JTextPane textGoesHereTextPane1;
    private JTextPane textGoesHereTextPane;
    private JPanel rightTextPanel;

    public GUILayout()
    {
        createTable();

    }
    public JPanel getRootPanel()
    {
        return rootPanel;
    }
    public void createTable()
    {
        //Test Data
        Object[][] data =
        {
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
            {"Process 1", "100ms","1","AAAAA"},
            {"Process 2", "10ms","2","BBBBB"},
            {"Process 3", "50ms","3","CCCCC"},
        };
        processTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Process Name", "Time","Priority","Example"}
        ));
    }
}
