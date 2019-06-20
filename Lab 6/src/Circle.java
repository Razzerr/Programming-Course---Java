import java.awt.*;

class Circle {
    Point centre, radiusPoint;
    double radius = 0;
    double scale = 1;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Circle(Point a, Point b) {
        centre = a;
        radiusPoint = new Point((int)scale*b.x, (int)scale*b.y);
    }
}
