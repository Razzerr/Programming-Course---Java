import java.awt.*;

class Rectangle {
    Point point;
    int distanceX, distanceY;
    double scale = 1;

    Color color = Color.BLACK;
    boolean fill = false;
    Color fillColor = Color.white;

    Rectangle(Point a, int b, int c) {
        point = a;
        distanceX = b;
        distanceY = c;
    }
}
