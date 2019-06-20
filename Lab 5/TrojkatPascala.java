import javax.swing.*;
import java.awt.EventQueue;

public class TrojkatPascala {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        });
    }
}

public class MyFrame extends JFrame {
    public MyFrame() {
        super("Pascal's Triangle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1280,720);
    }
}