import java.awt.*;
import java.util.ArrayList;

class Polygon {
    ArrayList<Integer> pointsX;
    ArrayList<Integer> pointsY;
    double scale = 1;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Polygon(ArrayList<Integer> a, ArrayList<Integer> b){
        pointsX = a;
        pointsY = b;
    }
}
