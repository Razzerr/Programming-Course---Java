import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Applet implements ActionListener {
    //Initializing all the necessary components.
    Thread t;
    JButton createCircle, createRectangle, createPolygon, modeModification, modeCreation, reset;
    JPanel menuUp, menu, menu2, drawingArea;
    int mode = 0; //Created in order to follow which figure is going to be drawn.
    Point tempPoint;

    ArrayList<Circle> circles = new ArrayList<>();
    ArrayList<Rectangle> rects = new ArrayList<>();
    ArrayList<Polygon> polygons = new ArrayList<>();
    ArrayList<Integer> polygonPointsX = new ArrayList<>();
    ArrayList<Integer> polygonPointsY = new ArrayList<>();

    public void init() {
        /////////////////////////////////////
        //Setting 'null' layout - personal.//
        /////////////////////////////////////
        this.setLayout(null);

        ///////////////////////////////////////////////////////////////////
        //Setting up the JPanels - upper menu, menu and the drawing area.//
        ///////////////////////////////////////////////////////////////////
        menu = new JPanel();
        menu.setLayout(null);
        menu.setBounds(0, 50, getWidth() / 4, getHeight());
        menu.setBackground(Color.LIGHT_GRAY);

        menu2 = new JPanel();
        menu2.setLayout(null);
        menu2.setBounds(0, 50, getWidth() / 4, getHeight());
        menu2.setBackground(Color.LIGHT_GRAY);
        menu2.setVisible(false);

        menuUp = new JPanel();
        menuUp.setLayout(null);
        menuUp.setBounds(0, 0, getWidth() / 4, 50);
        menuUp.setBackground(Color.GRAY);


        drawingArea = new JPanel();
        drawingArea.setLayout(null);
        drawingArea.setBounds(getWidth() / 4, 0, getWidth(), getHeight());
        drawingArea.setBackground(Color.white);
        drawingArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if(modeCreation.getBackground()==Color.LIGHT_GRAY) {
                    creation(me);
                }
                else{
                    figureChoosing(me);
                }
            }
        });
       drawingArea.addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent we) {
                if(modeCreation.getBackground()==Color.GRAY) {
                    sizeManipulation(we);
                }
            }
        });

        /////////////////////////////////////
        //Setting up JButtons in the menus.//
        /////////////////////////////////////
        modeCreation = new JButton("Creation Mode");
        modeCreation.setBounds(0, 0, getWidth() / 8 - 25, 50);
        modeCreation.setBackground(Color.LIGHT_GRAY);
        modeCreation.setBorder(null);
        modeCreation.addActionListener(this);
        modeCreation.setActionCommand("Creation");
        menuUp.add(modeCreation);

        modeModification = new JButton("Modification Mode");
        modeModification.setBounds(getWidth() / 8 - 25, 0, getWidth() / 8 - 25, 50);
        modeModification.setBackground(Color.GRAY);
        modeModification.setBorder(null);
        modeModification.addActionListener(this);
        modeModification.setActionCommand("Modification");
        menuUp.add(modeModification);

        reset = new JButton("R");
        reset.setBounds(getWidth() / 4 - 50, 0, 50, 50);
        reset.setBackground(Color.red);
        //reset.setBorder(null);
        reset.addActionListener(this);
        reset.setActionCommand("Reset");
        menuUp.add(reset);

        createCircle = new JButton("Circle");
        createCircle.setBounds(0, 0, getWidth() / 4, (getHeight() - 50) / 3);
        createCircle.setBackground(Color.LIGHT_GRAY);
        createCircle.setBorder(null);
        createCircle.addActionListener(this);
        createCircle.setActionCommand("Circle");
        menu.add(createCircle);

        createRectangle = new JButton("Rectangle");
        createRectangle.setBounds(0, (getHeight() - 50) / 3, getWidth() / 4, (getHeight() - 50) / 3);
        createRectangle.setBackground(Color.LIGHT_GRAY);
        createRectangle.setBorder(null);
        createRectangle.addActionListener(this);
        createRectangle.setActionCommand("Rectangle");
        menu.add(createRectangle);

        createPolygon = new JButton("Polygon");
        createPolygon.setBounds(0, 2 * (getHeight() - 50) / 3, getWidth() / 4, (getHeight() - 50) / 3);
        createPolygon.setBackground(Color.LIGHT_GRAY);
        createPolygon.setBorder(null);
        createPolygon.addActionListener(this);
        createPolygon.setActionCommand("Polygon");
        menu.add(createPolygon);

        ////////////////////////////////////////
        //Adding all the panels to the applet.//
        ////////////////////////////////////////
        add(menu);
        add(menuUp);
        add(menu2);
        add(drawingArea);
    }

/*    public void start() {
        t = new Thread(this);
        t.start();
    }

    public void run(){
        for( ; ; ) {
            try {
                menuUp.repaint();
                menu.repaint();
                menu2.repaint();
                Thread.sleep(250);
                //modeModification.setText(Integer.toString(mode));
                //drawingArea.repaint();
            }
            catch(InterruptedException e) {}
        }
    }*/

    public void paint(Graphics g) {
        for (Circle i : circles) {
            int rad = (int) i.radius;
            g.setColor(i.color);
            g.drawOval(i.centre.x - rad / 2 + getWidth() / 4, i.centre.y - rad / 2, rad, rad);
            if (i.fill) {
                g.setColor(i.fillColor);
                g.fillOval(i.centre.x - rad / 2 + getWidth() / 4, i.centre.y - rad / 2, rad, rad);
            }
        }
        for (Rectangle i : rects) {
            g.setColor(i.color);
            g.drawRect(i.sPoint.x + getWidth() / 4, i.sPoint.y, i.distanceX, i.distanceY);
            if (i.fill) {
                g.setColor(i.fillColor);
                g.fillRect(i.sPoint.x + getWidth() / 4, i.sPoint.y, i.distanceX, i.distanceY);
            }
        }
        for (Polygon i : polygons) {
            g.setColor(i.color);
            int[] pX = convertIntegers(i.pointsX);
            int[] pY = convertIntegers(i.pointsY);

            g.drawPolygon(pX, pY, pX.length);
            if (i.fill) {
                g.setColor(i.fillColor);
                g.drawPolygon(pX, pY, pX.length);
            }
        }
        menuUp.repaint();
        menu.repaint();
        menu2.repaint();
    }


    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Creation":
                modeCreation.setBackground(Color.LIGHT_GRAY);
                modeModification.setBackground(Color.GRAY);
                menu.setVisible(true);
                menu2.setVisible(false);
                mode = 0;
                break;
            case "Modification":
                modeCreation.setBackground(Color.GRAY);
                modeModification.setBackground(Color.LIGHT_GRAY);
                menu2.setVisible(true);
                menu.setVisible(false);
                colorReset();
                mode = 1;
                break;
            case "Circle":
                mode = 11;
                colorReset();
                createCircle.setBackground(Color.GRAY);
                break;
            case "Rectangle":
                mode = 21;
                colorReset();
                createRectangle.setBackground(Color.GRAY);
                break;
            case "Polygon":
                colorReset();
                createPolygon.setBackground(Color.GRAY);
                mode = 31;
                break;
            case "Reset":
                colorReset();
                reset();
                break;
        }
    }

    void creation(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            switch (mode) {
                case 11:
                    tempPoint = me.getPoint();
                    mode = 12;
                    break;
                case 12:
                    circles.add(0, new Circle(tempPoint, me.getPoint()));
                    circles.get(0).radius = 2 * (circles.get(0).centre.distance(circles.get(0).radiusPoint));
                    colorReset();
                    repaint();
                    mode = 0;
                    break;
                case 21:
                    tempPoint = me.getPoint();
                    mode = 22;
                    break;
                case 22:
                    rects.add(0, new Rectangle(tempPoint, me.getPoint()));
                    rects.get(0).distanceX = Math.abs(rects.get(0).point1.x - rects.get(0).point2.x);
                    rects.get(0).distanceY = Math.abs(rects.get(0).point1.y - rects.get(0).point2.y);
                    rects.get(0).sPoint = new Point(Math.min(rects.get(0).point1.x, rects.get(0).point2.x),
                            Math.min(rects.get(0).point1.y, rects.get(0).point2.y));
                    colorReset();
                    repaint();
                    mode = 0;
                    break;

                case 31:
                    tempPoint = me.getPoint();
                    polygonPointsX.add(me.getPoint().x + getWidth() / 4);
                    polygonPointsY.add(me.getPoint().y);
                    mode = 32;
                    break;
                case 32:
                    polygonPointsX.add(me.getPoint().x + getWidth() / 4);
                    polygonPointsY.add(me.getPoint().y);
                    if (me.getPoint().distance(tempPoint) < 10) {
                        polygons.add(new Polygon(polygonPointsX, polygonPointsY));
                        colorReset();
                        repaint();
                        polygonPointsX = new ArrayList<>();
                        polygonPointsY = new ArrayList<>();
                        mode = 0;
                    }
                    break;
            }
        }
        else if (me.getButton() == MouseEvent.BUTTON3){
            cancel();
        }
    }


    Circle best = new Circle(new Point(0,0),new Point(0,0));
    Rectangle bestr;
    Polygon bestp;
    int a =0;

    void figureChoosing(MouseEvent me){
        if (me.getButton() == MouseEvent.BUTTON1) {
            for (Circle i : circles) {
                if (me.getPoint().distance(i.radiusPoint) - i.radius < me.getPoint().distance(best.radiusPoint) - best.radius ) {
                    best = null;
                    best = i;
                    repaint();
                    modeModification.setText(Double.toString(me.getPoint().distance(i.radiusPoint) - i.radius));
                }
            }

            /*for (Rectangle i: rects){

            }
            for (Polygon i: polygons){

            }*/
        }
    }

    void sizeManipulation(MouseWheelEvent we){

        circles.get(circles.indexOf(best)).scale -= we.getUnitsToScroll();
        a+=we.getUnitsToScroll();
        //modeModification.setText(Integer.toString(a));
        modeModification.setText(Double.toString(circles.get(circles.indexOf(best)).scale));
        repaint();
    }

    void colorReset() {
        createCircle.setBackground(Color.LIGHT_GRAY);
        createRectangle.setBackground(Color.LIGHT_GRAY);
        createPolygon.setBackground(Color.LIGHT_GRAY);
    }

    void reset() {
        circles = new ArrayList<>();
        rects = new ArrayList<>();
        polygons = new ArrayList<>();
        polygonPointsX = new ArrayList<>();
        polygonPointsY = new ArrayList<>();
        mode = 0;
        repaint();
    }

    void cancel() {
        polygonPointsX = new ArrayList<>();
        polygonPointsY = new ArrayList<>();
        colorReset();
        mode = 0;
    }

    int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}