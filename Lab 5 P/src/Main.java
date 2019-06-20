import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;

public class Main {
    private static final int max = 20;
    private static int width = 1280;
    private static int height = 720;
    private static int rows = 5;
    private static Font font = new Font("Times new roman", Font.BOLD ,(height-60)/(3*rows));

    public static void main(String[] args) {
        String[][] showTable = pasTableGenerator();

        //Frame setup
        JFrame mainFrame = new JFrame("Pascal's Triangle Generator");
        mainFrame.setLayout(null);
        mainFrame.setSize(width, height);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Text setup
        JTextField staticTextField = new JTextField("Please choose how many rows would you like to display.");
        staticTextField.setBounds(0,0, 2*width/3,20);

        JTextField[][] pasTriangle = new JTextField[max][max];

        for (int i = 0; i<max; i++){
            for (int j = 0; j<max; j++){
                pasTriangle[i][j] = new JTextField();
                pasTriangle[i][j].setText(showTable[j][i]);
                pasTriangle[i][j].setBounds(i*(width-10)/rows, 20+j*(height-60)/rows, (width-10)/rows, (height-60)/rows);
               // pasTriangle[i][j].setBorder(null);
                pasTriangle[i][j].setHorizontalAlignment(JTextField.CENTER);
                pasTriangle[i][j].setFont(font);
                if (!pasTriangle[i][j].getText().equals("0")){
                    pasTriangle[i][j].setBackground(java.awt.Color.LIGHT_GRAY);
                } else{
                    pasTriangle[i][j].setBackground(java.awt.Color.PINK);
                }
                mainFrame.add(pasTriangle[i][j]);
            }
        }

        //Slider setup
        JSlider valSlide = new JSlider(2,max,5);
        valSlide.setBounds(2*width/3, 0, width/3-10,20);
        valSlide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rows = valSlide.getValue();
                for (int i = 0; i<rows; i++){
                    for (int j=0; j<rows; j++){
                        pasTriangle[i][j].setBounds(i*(width-10)/rows, 20+j*(height-60)/rows, (width-6)/rows, (height-60)/rows);
                        font = new Font("Times new roman", Font.BOLD ,(height-60)/(3*rows));
                        pasTriangle[i][j].setFont(font);
                    }
                }
                mainFrame.repaint();
            }
        });

        //Final frame setup
        mainFrame.add(staticTextField);
        mainFrame.add(valSlide);
        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                width = mainFrame.getWidth();
                height = mainFrame.getHeight();
                staticTextField.setBounds(0,0, 2*width/3,20);
                valSlide.setBounds(2*width/3, 0, width/3-10, 20);
                for (int i = 0; i<rows; i++){
                    for (int j=0; j<rows; j++){
                        pasTriangle[i][j].setBounds(i*(width-10)/rows, 20+j*(height-60)/rows, (width)/rows, (height-60)/rows);
                        font = new Font("Times new roman", Font.BOLD ,(height-60)/(3*rows));
                        pasTriangle[i][j].setFont(font);
                    }
                }
            }
        });
        mainFrame.setVisible(true);
    }

    private static String[][] pasTableGenerator(){
        int[][] result = new int[max][max];
        String[][] finRes = new String[max][max];

        for (int h = 0; h < max; h++) {
            for (int i = 0; i < max; i++) {
                if (i>h){
                    result[h][i]=0;
                    finRes[h][i]="0";
                }
                else if (i==0){
                    result[h][i]=1;
                    finRes[h][i]="1";
                }
                else{
                    result[h][i]=result[h-1][i-1]+result[h-1][i];
                    finRes[h][i]=Integer.toString(result[h-1][i-1]+result[h-1][i]);
                }
            }
        }
        return finRes;
    }
}
