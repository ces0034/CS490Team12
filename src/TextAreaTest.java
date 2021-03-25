import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TextAreaTest implements Runnable{

    GUILayout ui = new GUILayout();
    JTextArea area1 = ui.getJTextAreaOne();
    JTextArea area2 = ui.getJTextAreaTwo();
    DefaultTableModel model = ui.getTableModel();


    public void run()
    {
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(textRunner).start();
    }
    Runnable textRunner = new Runnable()
    {
        int i = 0;
        public void run()
        {
            try
            {
                while(true)
                {
                    area2.append(new java.util.Date().toString()+"\n");
                    area2.append("Hello, This is a thread!\n");

                    area1.append(new java.util.Date().toString()+"\n");
                    area1.append("Hello, This is a thread!\n");

                    model.insertRow(model.getRowCount(), new Object[]{"duplicates.Process "+i, "A" +i, "B"+i,"C"+i});
                    i++;
                    Thread.sleep(0);
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    };
}
