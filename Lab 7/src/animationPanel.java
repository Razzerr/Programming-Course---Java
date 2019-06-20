import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/**Class of a custom JFrame. Object of this class becomes the animation area.*/
class animationPanel extends JFrame implements ComponentListener {
    /*Declaring constant values*/
    private short width = 1280, height = 720, n, m, winBar = 31, winLeft = 7;
    /*winBar and winLeft are constants, informing painting area how many pixels from the frame are 'reserved' for
    Windows' window. Thus, the animation area looks equal.*/
    ArrayList<Point> rabbitsCoord;
    Wolf wolf;
    boolean refill = true; /*If the window was resized, informs "paint" function to draw white background beneath the
    animation area*/

    animationPanel(short n, short m, ArrayList<Point> rabbitsCoord, Wolf wolf) {
        this.n = n;
        this.m = m;
        this.rabbitsCoord = rabbitsCoord;
        this.wolf = wolf;

        this.setSize(width, height);
        this.setLayout(null);
        this.setVisible(true);
        this.setTitle("Let the hunt begin!");
        this.addComponentListener(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**Painting method. Also acts like a 'map' checking if the currently painted area contains the wolf or a rabbit.*/
    public void paint(Graphics g) {
        if (refill) {
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == wolf.y && j == wolf.x) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                    g.setColor(Color.black);
                    g.drawRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                } else if (rabbitsCoord.contains(new Point(j, i))) {
                    g.setColor(Color.orange);
                    g.fillRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                    g.setColor(Color.black);
                    g.drawRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                } else {
                    g.setColor(Color.white);
                    g.fillRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                    g.setColor(Color.black);
                    g.drawRect(j * ((getWidth() - winLeft) / n) + winLeft, i * ((getHeight() - winBar) / m) + winBar,
                            (getWidth() - winLeft) / n, (getHeight() - winBar) / m);
                }
            }
        }
        refill = false;
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        refill = true;
        repaint();
    }
}
