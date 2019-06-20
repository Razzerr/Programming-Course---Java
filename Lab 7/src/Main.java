import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**This program is an example of threading in Java. It's called "The wolf and the rabbits", and it creates separate threads
 *to implement a simple simulation:
 * - Wolf is the hunter. It's solely existence purpose is to hunt, and eventually eat, rabbits. After the wolf eats
 * a rabbit, it has to take a little siesta, which lasts for about 5 cycles of entities movements. (One cycle = every
 * entity - rabbits and the wolf - attempted to move to one, adjacent area). The wolf always hunts the closest rabbit,
 * and after all the rabbits are eaten, he can finally go to the eternal sleep.
 * - Rabbits are the prey. They try to run away from the wolf. Unfortunately, rabbits are neither as crafty nor as
 * smart as the wolf :(. When they meet the fence (window boundary) they are getting confused. They can behave like
 * a fly next to a window, still trying to go through the fence even though it's impossible, but they can even start
 * moving towards the hunter. Poor little beings...
 * @author  Michal Budnik
 * @version 1.0
 * @since   2017-05-22
 */
public class Main {
    /*Declaring constant values*/
    private static final short n = 25, m = 25, rabbitNumber = 200, k = 100;
    private static ArrayList<Rabbit> rabbits = new ArrayList<>();
    private static ArrayList<Point> rabbitsCoord = new ArrayList<>();//List containing the coordinates of all the rabbits
    private static short randomBool = randomGenerator(3), eaten = 0; //eaten - counter for Wolf's sleep
    private static Wolf wolf;
    private static Thread wolfT;
    private static Thread[] rabbitsT;
    private static animationPanel playArea;

    /**The 'main' method controls major processes of the program - like random generation, threads start and so on.*/
    public static void main(String[] args) {
        //Shorts keeping randomly generated coordinates
        short x = randomGenerator(m);
        short y = randomGenerator(n);

        wolf = new Wolf(x, y, rabbitsCoord);

        for (int i = 0; i < rabbitNumber; i++) {
            x = randomGenerator(m);
            y = randomGenerator(n);
            if ((x!=wolf.x && y!=wolf.y)&&(!rabbitsCoord.contains(new Point(x,y)))) {
                rabbitsCoord.add(new Point(x,y));
                rabbits.add(new Rabbit(x, y, n, m, wolf, rabbits, randomBool));
            }
        }

        playArea = new animationPanel(n, m, rabbitsCoord, wolf);

        //Main loop, calls the thread functions till there is no rabbits
        do {
            if (eaten == 0){
                notEaten();
            }else if (eaten !=0){
                eaten();
                eaten+=1;
                if (eaten == 5){eaten = 0;}
            }
        } while (rabbits.size() != 0);
        playArea.repaint();

    }

    /**Random integer generator
     * @param max Tells the method what the maximum range should be.
     * @return A random (short) integer from the [0, max) range.*/
    private static short randomGenerator(int max) {
        Random random = new Random();
        return (short) random.nextInt(max);
    }

    /**Method calling out wolf and rabbit processes. Checks if the wolf ate any sheep in the given movement cycle.*/
    private static void notEaten(){
        startWolf();
        Rabbit tempRabbit = null;
        for (Rabbit r : rabbits) {
            if (r.x == wolf.x && r.y == wolf.y) {
                tempRabbit = r;
            }
        }
        if (tempRabbit != null) {
            //If the wolf has eaten a rabbit, removing the rabbit from the list, and changing "eaten" counter to one
            rabbits.remove(tempRabbit);
            eaten+=1;
        }

        startRabbits();
    }

    /**Calls only rabbit processes, up to 5 turns after the wolf ate a rabbit.*/
    private static void eaten(){
        startRabbits();
    }

    /**Method starting rabbit threads, and waiting for their synchronisation. Each thread runs independently in order
     * to avoid two rabbit threads wanting to occupy the same position.*/
    private static void startRabbits() {
        //Creating n threads, where n is the number of rabbits
        rabbitsT = new Thread[rabbits.size()];
        for (int i = 0; i<rabbits.size(); i++) {
            randomBool = randomGenerator(3);
            rabbits.get(i).randomBool = randomBool; //Feeding given process with a randomly generated value
            rabbitsT[i] = new Thread(rabbits.get(i), "Prey");
            rabbitsT[i].start();
            try {
                rabbitsT[i].sleep(k/rabbits.size());
                rabbitsT[i].join();
            } catch (InterruptedException ex) {
            }
            updateData();
        }
        rabbitsT = null;
    }

    /**Method starting the wolf thread.*/
    private static void startWolf() {
        wolfT = new Thread(wolf, "Hunter");
        wolfT.start();
        try {
            wolfT.sleep(k/rabbits.size());
            wolfT.join();
        } catch (InterruptedException ex) {
        }
        updateData();
    }

    /**Method updating list with rabbit coordinates. It's necessary for appropriate display of animation.*/
    private static void updateData(){
        rabbitsCoord = new ArrayList<>();
        for (Rabbit r: rabbits){
            rabbitsCoord.add(new Point(r.x, r.y));
        }
        playArea.rabbitsCoord = rabbitsCoord;
        wolf.rabbitsCoord = rabbitsCoord;
        playArea.repaint();
    }
}
