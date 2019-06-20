import java.awt.*;
import java.util.ArrayList;

/**Wolf's class. Contains list of rabbit coordinates to monitor where to move.*/
public class Wolf implements Runnable{
    short x, y;
    ArrayList<Point> rabbitsCoord;

    Wolf(short x, short y, ArrayList<Point> rabbits){
        this.x = x;
        this.y = y;
        this.rabbitsCoord = rabbits;
    }

    @Override
    public void run() {
        short bestX = -1, bestY = -1;
        //Checking the closest rabbit
        for (Point r: rabbitsCoord){
            if (bestX == -1){
                bestX = (short)r.x;
                bestY = (short)r.y;
            }
            else if (Math.sqrt((int)Math.pow((this.x - r.x), 2) + (int)Math.pow((this.y - r.y), 2))
                    < Math.sqrt((int)Math.pow((this.x - bestX), 2) + (int)Math.pow((this.y - bestY), 2))){
                bestX = (short)r.x;
                bestY = (short)r.y;
            }
        }

        //Setting the vector in which direction should the wolf move
        short signX = 1, signY = 1;
        if (bestX-this.x<0){ signX = -1;}
        else if (bestX - this.x == 0){signX = 0;}
        if (bestY-this.y<0){ signY = -1;}
        else if (bestY - this.y == 0){signY = 0;}

        //Changing wolf's position
        this.x+=signX;
        this.y+=signY;
    }
}
