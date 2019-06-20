import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class myColorsMenu extends JPanel implements ActionListener{
    JButton nullify, white, lightGray, gray, darkGray, black, yellow, orange, red, magenta, pink, green, cyan, blue;
    Color color = null;

    private ArrayList _listeners = new ArrayList();
    public synchronized void addColorListener(colorChosenListener listener)  {
        _listeners.add(listener);
    }
    public synchronized void removeColorListener(colorChosenListener listener)   {
        _listeners.remove(listener);
    }
    private synchronized void fireEvent() {
        colorChosenEvent event = new colorChosenEvent(this);
        Iterator i = _listeners.iterator();
        while(i.hasNext())  {
            ((colorChosenListener) i.next()).colorWasChosen(event);
        }
    }

    myColorsMenu(){
        setLayout(null);
        setBackground(Color.white);
        setBounds(0, 0, 100, 60);
        setVisible(false);

        white = new JButton();
        white.setBackground(Color.white);
        white.setBounds(0, 0, 20, 20);
        white.setBorder(null);
        white.addActionListener(this);
        white.setActionCommand("white");

        lightGray = new JButton();
        lightGray.setBackground(Color.lightGray);
        lightGray.setBounds(20, 0, 20, 20);
        lightGray.setBorder(null);
        lightGray.addActionListener(this);
        lightGray.setActionCommand("lightGray");

        gray = new JButton();
        gray.setBackground(Color.gray);
        gray.setBounds(40, 0, 20, 20);
        gray.setBorder(null);
        gray.addActionListener(this);
        gray.setActionCommand("gray");

        darkGray = new JButton();
        darkGray.setBackground(Color.darkGray);
        darkGray.setBounds(60, 0, 20, 20);
        darkGray.setBorder(null);
        darkGray.addActionListener(this);
        darkGray.setActionCommand("darkGray");

        black = new JButton();
        black.setBackground(Color.black);
        black.setBounds(80, 0, 20, 20);
        black.setBorder(null);
        black.addActionListener(this);
        black.setActionCommand("black");

        yellow = new JButton();
        yellow.setBackground(Color.yellow);
        yellow.setBounds(0, 20, 20, 20);
        yellow.setBorder(null);
        yellow.addActionListener(this);
        yellow.setActionCommand("yellow");

        orange = new JButton();
        orange.setBackground(Color.orange);
        orange.setBounds(20, 20, 20, 20);
        orange.setBorder(null);
        orange.addActionListener(this);
        orange.setActionCommand("orange");

        red = new JButton();
        red.setBackground(Color.red);
        red.setBounds(40, 20, 20, 20);
        red.setBorder(null);
        red.addActionListener(this);
        red.setActionCommand("red");

        magenta = new JButton();
        magenta.setBackground(Color.magenta);
        magenta.setBounds(60, 20, 20, 20);
        magenta.setBorder(null);
        magenta.addActionListener(this);
        magenta.setActionCommand("magenta");

        pink = new JButton();
        pink.setBackground(Color.pink);
        pink.setBounds(80, 20, 20, 20);
        pink.setBorder(null);
        pink.addActionListener(this);
        pink.setActionCommand("pink");

        green = new JButton();
        green.setBackground(Color.green);
        green.setBounds(0, 40, 20, 20);
        green.setBorder(null);
        green.addActionListener(this);
        green.setActionCommand("green");

        cyan = new JButton();
        cyan.setBackground(Color.cyan);
        cyan.setBounds(20, 40, 20, 20);
        cyan.setBorder(null);
        cyan.addActionListener(this);
        cyan.setActionCommand("cyan");

        blue = new JButton();
        blue.setBackground(Color.blue);
        blue.setBounds(40, 40, 20, 20);
        blue.setBorder(null);
        blue.addActionListener(this);
        blue.setActionCommand("blue");

        nullify = new JButton("N");
        nullify.setBackground(Color.white);
        nullify.setBounds(60,40,20,20);
        nullify.addActionListener(this);
        nullify.setActionCommand("nullify");

        add(white);
        add(lightGray);
        add(gray);
        add(darkGray);
        add(black);
        add(yellow);
        add(orange);
        add(red);
        add(magenta);
        add(pink);
        add(green);
        add(cyan);
        add(blue);
        add(nullify);
    }

    Color getClr(){
        return color;
    }
    void setClr(Color o){
        color = o;
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "white":
                color = Color.white;
                break;
            case "lightGray":
                color = Color.lightGray;
                break;
            case "gray":
                color = Color.gray;
                break;
            case "darkGray":
                color = Color.darkGray;
                break;
            case "black":
                color = Color.black;
                break;
            case "yellow":
                color = Color.yellow;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "red":
                color = Color.red;
                break;
            case "magenta":
                color = Color.magenta;
                break;
            case "pink":
                color = Color.pink;
                break;
            case "green":
                color = Color.green;
                break;
            case "cyan":
                color = Color.cyan;
                break;
            case "blue":
                color = Color.blue;
                break;
            case "nullify":
                color = null;
                break;
        }
        fireEvent();
    }

}
