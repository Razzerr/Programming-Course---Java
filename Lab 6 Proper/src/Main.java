import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Main extends JApplet implements ActionListener {
    //Initializing all the necessary components.
    JPanel menu;
    JTextPane text;
    DrawingPanel draw;
    JButton createCircle, createRectangle, createPolygon, info, reset, save, load;
    JTextArea infoMessage;
    int count = 0;

    public void init() {
        this.setLayout(null);

        //Setting Panels
        menu = new JPanel();
        menu.setLayout(null);
        menu.setBounds(0, 0, getWidth(), 70);
        menu.setBackground(Color.lightGray);
        menu.setBorder(null);

        draw = new DrawingPanel();
        draw.setLayout(null);
        draw.setBounds(0, 70, getWidth(), getHeight());
        draw.setBorder(null);
        draw.setBackground(Color.WHITE);

        text = new JTextPane();
        text.setLayout(null);
        text.setBounds(0, 70, getWidth(), getHeight());
        text.setBorder(null);
        text.setVisible(false);

        //Setting buttons
        createCircle = new JButton("Circle");
        createCircle.setBounds(10, 10, 50, 50);
        createCircle.setBackground(Color.WHITE);
        createCircle.setBorder(null);
        createCircle.addActionListener(this);
        createCircle.setActionCommand("Circle");
        menu.add(createCircle);

        createRectangle = new JButton("Rectangle");
        createRectangle.setBounds(70, 10, 50, 50);
        createRectangle.setBackground(Color.WHITE);
        createRectangle.setBorder(null);
        createRectangle.addActionListener(this);
        createRectangle.setActionCommand("Rectangle");
        menu.add(createRectangle);

        createPolygon = new JButton("Polygon");
        createPolygon.setBounds(130, 10, 50, 50);
        createPolygon.setBackground(Color.WHITE);
        createPolygon.setBorder(null);
        createPolygon.addActionListener(this);
        createPolygon.setActionCommand("Polygon");
        menu.add(createPolygon);

        save = new JButton("Save");
        save.setBounds(getWidth() - 240, 10, 50, 50);
        save.setBackground(Color.WHITE);
        save.setBorder(null);
        save.addActionListener(this);
        save.setActionCommand("Save");
        menu.add(save);

        load = new JButton("Load");
        load.setBounds(getWidth() - 180, 10, 50, 50);
        load.setBackground(Color.WHITE);
        load.setBorder(null);
        load.addActionListener(this);
        load.setActionCommand("Load");
        menu.add(load);

        reset = new JButton("Reset");
        reset.setBounds(getWidth() - 60, 10, 50, 50);
        reset.setBackground(Color.WHITE);
        reset.setBorder(null);
        reset.addActionListener(this);
        reset.setActionCommand("Reset");
        menu.add(reset);

        info = new JButton("Info");
        info.setBounds(getWidth() - 120, 10, 50, 50);
        info.setBackground(Color.WHITE);
        info.setBorder(null);
        info.addActionListener(this);
        info.setActionCommand("Info");
        menu.add(info);

        //Final setting up
        infoMessage = new JTextArea(message);
        infoMessage.setBounds(0, 0, getWidth(), getHeight());
        infoMessage.setBackground(Color.LIGHT_GRAY);
        infoMessage.setAlignmentX(getWidth()/2);

        text.add(infoMessage);

        add(text);
        add(menu);
        add(draw);
    }

    //Listener for all the buttons
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Circle":
                buttonReset();
                draw.cancel();
                draw.setMode(1);
                break;
            case "Rectangle":
                buttonReset();
                draw.cancel();
                draw.setMode(2);
                break;
            case "Polygon":
                buttonReset();
                draw.cancel();
                draw.setMode(3);
                break;
            case "Reset":
                buttonReset();
                draw.reset();
                draw.repaint();
                break;
            case "Info":
                draw.cancel();
                if (count == 0) {
                    draw.cancel();
                    info.setBackground(Color.GRAY);
                    draw.setVisible(false);
                    text.setVisible(true);
                    count = 1;
                } else {
                    info.setBackground(Color.WHITE);
                    draw.setVisible(true);
                    text.setVisible(false);
                    count = 0;
                }
                break;
            case "Save":
                save(draw.figures);
                break;
            case "Load":
                draw.figures = load();
                break;
        }
    }

    void buttonReset() {
        info.setBackground(Color.WHITE);
        draw.setVisible(true);
        text.setVisible(false);
        count = 0;
    }

    void save(ArrayList<Figure> data){
        try {
            File file = new File("C:\\Users\\micha\\Desktop\\t.tmp");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    ArrayList<Figure> load(){
        ArrayList<Figure> temp = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (ArrayList<Figure>) ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return temp;
    }

    String message = "Welcome to Paint9000!\nThe aim of this program is to allow anyone in this world (independently " +
            "of race, religion or political views) to express themselves through art! I truly hope it'll become a tool " +
            "for some artists to achieve their life goals.\n\n Author: Michał Budnik";
}