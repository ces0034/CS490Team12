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
    private JTable processTable;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JTextPane textPane1;
    private JTextPane textPane;
    private JPanel rightTextPanel;
    private JTable table2;
    private JLabel Process;
    private JLabel queueLabel;
    private JLabel labelProcess;
    public JTextArea textArea1;
    private JTextArea textArea2;


    private Object[][] data =
            {
            };

   private DefaultTableModel tableModel = new DefaultTableModel(
            data,
            new String[]{"Process Name", "Arrival Time","Service Time","Priority"}
    );
    private DefaultTableModel tableModel2 = new DefaultTableModel(
            data,
            new String[]{"Process Name", "Arrival Time","Service Time","Priority"}
    );


    public GUILayout()
    {
        createTable();
        createTable2();

    }
    public JPanel getRootPanel()
    {
        return rootPanel;
    }

    public JTextArea getJTextAreaOne()
    {
        return textArea1;
    }

    public JTextArea getJTextAreaTwo()
    {
        return textArea2;
    }
    public DefaultTableModel getTableModel()
    {
        return tableModel;
    }
    public DefaultTableModel getTableModel2()
    {
        return tableModel2;
    }
    public void createTable()
    {

        processTable.setModel(tableModel);
    }
    public void createTable2()
    {
        //Test Data
        Object[][] data =
                {
                        {"Process 1", "100ms","1","AAAAA"},
                        {"Process 2", "10ms","2","BBBBB"},
                        {"Process 3", "50ms","3","CCCCC"},

                };
        table2.setModel(tableModel2);
    }
}
