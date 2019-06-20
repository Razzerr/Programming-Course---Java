import java.util.ArrayList;

/**Rabbit's class. Is a blueprint for all the rabbits, contains wolf's information to know where to run away.*/
public class Rabbit implements Runnable {
    short x, y, randomBool; //randomBool contains the randomly generated number in case rabbit gets next to a wall
    private short n, m;
    private Wolf wolf;
    private ArrayList<Rabbit> rabbits;

    Rabbit(short x, short y, short n, short m, Wolf wolf, ArrayList<Rabbit> rabbits, short randomBool) {
        this.x = x;
        this.y = y;
        this.wolf = wolf;
        this.n = n;
        this.m = m;
        this.rabbits = rabbits;
        this.randomBool = randomBool;
    }

    @Override
    public void run() {
        short signX = 1, signY = 1, tempX = chooseSignum(randomBool), tempY = chooseSignum(randomBool);
        //(tempX,tempY) is a randomly generated vector of rabbit's movement

        //Checking wolf's position and setting 'run away' route
        if (wolf.x - this.x > 0) {
            signX = -1;
        } else if (wolf.x - this.x == 0) {
            signX = tempX;
        }
        if (wolf.y - this.y > 0) {
            signY = -1;
        } else if (wolf.y - this.y == 0) {
            signY = tempY;
        }

        //Variable move will be set to false if the place where the rabbit wants to move is taken by another rabbit
        boolean move = true;

        //If rabbit is next to any wall it'll get randomly assigned direction of movement
        if (this.x + signX == m || this.x + signX == -1 || this.y + signY == n || this.y + signY == -1){
            signX = tempX;
            signY = tempY;
        }

        //If the rabbit is set to move out of the map (in given direction), it's not moving in a given direction at all
        if (this.x + signX == m || this.x + signX == -1) {
                signX = 0;
            }
        if (this.y + signY == n || this.y + signY == -1) {
                signY = 0;
        }


        for (Rabbit r : rabbits) {
            if (r.x == this.x + signX && r.y == this.y + signY && r != this) {
                move = false;
            }
        }
        if (wolf.x == this.x + signX && wolf.y == this.y + signY){
            move = false;
        }
        if (move) {
            this.x += signX;
            this.y += signY;
        }
    }

    /**Function changing randomly generated number into a possible movement vector.
     * @param randomBool Randomly generated number from [0, 3).
     * @return Possible movement vector (x,y) from a set ([-1,1], [-1,1])*/
    private short chooseSignum(short randomBool) {
        short sign = 1;
        if (randomBool == 0){
            sign = 0;
        } else if (randomBool == 1) {
            sign = -1;
        }
        return sign;
    }
}
