import javax.swing.*;

/**
 * Created by micha on 07.05.2017.
 */
public class Test extends JPanel {
    int x;

    public Test(int passMe){
        super();
        x = passMe;
    }

    public void setX(int changeTo){
        x = changeTo; //could also make a method that says x++
    }

    public int getX(){
        return x;
    }
}