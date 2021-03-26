import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterFile {
    private JPanel RootPanel;
    private JButton launchButton;
    private JTextField textField;
    private JPanel titlePanel;
    private JLabel formTitle;



    public EnterFile() {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OS ui = new OS();
                ui.setFilename(textField.getText());
                EventQueue.invokeLater(ui);

            }
        });
    }

    public JPanel getRootPanel()
    {
        return RootPanel;
    }

    public JTextField getTextField()
    {
        return textField;
    }
    public String getTextPath()
    {
        return textField.getText();
    }

}
