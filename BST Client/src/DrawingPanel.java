import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    int width, height;
    int posX, posY = 20;
    ArrayList<Character> operations = new ArrayList<>();
    char[] tree;
    String temp;
    short consecutiveValDrawn = 0, level = 1;
    boolean valDrawn = false;


    DrawingPanel(int w, int h) {
        width = w;
        height = h;
        posX = width/2;
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        posX = width/2;
        posY = 20;
        if (tree!=null) {
            for (char i : tree) {
                temp = Character.toString(i);
                if (i == ',') {
                    level+=1;
                    g.drawLine(posX-2, posY+2, posX-((int)(800/Math.pow(level, 2))-15), posY + 28);
                    posX -= 800/Math.pow(level, 2);
                    posY+= 50;
                    posX -= consecutiveValDrawn*6;
                    operations.add(0, i);
                    consecutiveValDrawn = 0;
                    valDrawn = false;
                } else if (i == '/') {
                    level+=1;
                    g.drawLine(posX+2, posY+2, posX+((int)(800/Math.pow(level, 2))-15), posY + 28);
                    posX += 800/Math.pow(level, 2);
                    posY+= 50;
                    valDrawn = false;
                    operations.add(0, i);
                    posX -= consecutiveValDrawn*6;
                    consecutiveValDrawn = 0;
                } else if (i == '(') {
                    if (operations.get(0) == ',') {
                        posX += 800/Math.pow(level, 2);
                        posY -= 50;
                        //posX -= consecutiveValDrawn*6;
                    } else if (operations.get(0) == '/') {
                        posX -= 800/Math.pow(level, 2);
                        posY -= 50;
                        //posX -= consecutiveValDrawn*6;
                    }
                    level-=1;
                    valDrawn = false;
                    consecutiveValDrawn = 0;
                    operations.remove(0);
                } else {
                    if (valDrawn) {
                        posX+=6;
                        consecutiveValDrawn++;
                        g.drawString(temp, posX, posY);
                    }else{
                        g.drawString(temp, posX, posY);
                    }
                    valDrawn = true;
                }
            }
        }
    }
}
