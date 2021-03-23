import javax.swing.*;
import java.awt.*;

public class Main{



   public static void main(String[] args)
    {
        //EventQueue.invokeLater(new TextAreaTest());
        //EventQueue.invokeLater(new TableTest());
        OS os = new OS();
        os.storeProcesses();
        EventQueue.invokeLater(new OS());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
               // createGUI();
               // testOS();

            }
        });
    }

    private static void testOS()
    {
        OS test = new OS();
        test.storeProcesses();
    }
    private static void createGUI()
    {
        GUILayout ui = new GUILayout();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
