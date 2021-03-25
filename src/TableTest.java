import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableTest implements Runnable {
    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);
    public void run()
    {
        tableModel.addColumn("Languages");
        tableModel.insertRow(0, new Object[] { "CSS" });
        tableModel.insertRow(0, new Object[] { "HTML5" });
        tableModel.insertRow(0, new Object[] { "JavaScript" });
        tableModel.insertRow(0, new Object[] { "jQuery" });
        tableModel.insertRow(0, new Object[] { "AngularJS" });
        // adding a new row
        tableModel.insertRow(tableModel.getRowCount(), new Object[] { "ExpressJS" });
        JFrame f = new JFrame();
        f.setSize(550, 350);
        f.add(new JScrollPane(table));
        f.setVisible(true);
        new Thread(tableRunner).start();
    }
    Runnable tableRunner = new Runnable()
    {
        public void run()
        {
            try
            {
                while(true)
                {

                    tableModel.insertRow(tableModel.getRowCount(), new Object[]{"duplicates.Process New", "A", "B","C"});

                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    };
}
