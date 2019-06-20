import java.awt.*;

class Rectangle {
    Point point1, point2, sPoint;
    int distanceX, distanceY;
    double scale = 1;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Rectangle(Point a, Point b) {
        point1 = a;
        point2 = b;
    }
}
