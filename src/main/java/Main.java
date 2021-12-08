import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {

        Thread thread = new Thread(new Repository());
        thread.start();
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.add(new MainForm().getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
