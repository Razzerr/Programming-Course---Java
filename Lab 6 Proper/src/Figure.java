import java.awt.*;
import java.util.ArrayList;

class Figure {
    int type = 0; // Type of a figure: 1 - circle, 2 - rectangle, 3 - polygon

    ArrayList<Integer> pointsX;
    ArrayList<Integer> pointsY;
    Point mainPoint;
    double radius = 0;
    double distX = 0;
    double distY = 0;
    int xSum, ySum;
    Point massCentre;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Figure(Point a, double rad) {
        mainPoint = a;
        radius = rad;
        type = 1;
    }
    Figure(Point a, double distx, double disty){
        mainPoint = a;
        distX = distx;
        distY = disty;
        type = 2;
        massCentre = new Point(mainPoint.x+(int)distx, mainPoint.y+(int)disty);
    }
    Figure(ArrayList<Integer> px, ArrayList<Integer> py){
        pointsX = px;
        pointsY = py;
        type = 3;
        setMassCentre();
    }

    void setMassCentre(){
        xSum = 0;
        ySum = 0;
        for(int i=0; i<pointsX.size(); i++) {
            xSum+=pointsX.get(i);
            ySum+=pointsY.get(i);
        }
        massCentre = new Point(xSum/pointsX.size(), ySum/pointsY.size());
    }
}
