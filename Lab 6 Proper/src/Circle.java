import java.awt.*;

class Circle {
    Point centre;
    double radius = 0;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Circle(Point a, double rad) {
        centre = a;
        radius = rad;
    }
}