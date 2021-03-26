import javax.swing.*;

//opens up the Enter File Path Form
public class Main{



   public static void main(String[] args)
    {
        createEnterFile(); //create and launch the enter file path

    }

    //packs the Enter File Path Form and sets it to visible
    private static void createEnterFile()
    {
        EnterFile ui  = new EnterFile(); //create a new enter file form
        JPanel root = ui.getRootPanel(); //get the rootpanel
        JFrame frame = new JFrame(); //create a jframe
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits when you hit the close button
        frame.setContentPane(root); //set the content pane to the root panel
        frame.pack(); //pack
        frame.setLocationRelativeTo(null); //
        frame.setVisible(true); // set to visible (display it to the screen)

    }

}
