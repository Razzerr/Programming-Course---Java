/*Program checking a circumeference and an area of some figures. Usage for arguments:
        java figury string_of_figures_to_check arguemnts_for_each_figure

        Arguments:
            Quadrangle (c): side1 side2 side3 side4 angle
                Square: a a a a 90
                Rectangle: a a b b 90
                Rhombus: a a a a 0<x<90
            Circle (o): radius
            Pentagon (p): side_length
            Hexagon (s): side_length
*/

public class figury{
    public static void main(String[] args){
        try {
            char args_0_c[] = args[0].toCharArray();
            int q = args[0].length();
            Figura[] figs = new Figura[q];

            int count = 1;
            for (int i = 0; i < q; i++) {
                switch (args_0_c[i]) {
                    case 'o':
                        figs[i] = new Okrag(Float.parseFloat(args[count]));
                        System.out.print("A circle of radius " + args[count] + ": Circumference = "
                                + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                        count++;
                        break;

                    case 'c':
                        if (Float.parseFloat(args[count + 4]) == 90) {
                            if (args[count].equals(args[count + 1]) && args[count + 1].equals(args[count + 2]) &&
                                    args[count + 2].equals(args[count + 3])) {
                                figs[i] = new Kwadrat(Float.parseFloat(args[count]));
                                System.out.print("A square of side " + args[count] + ": Circumference = "
                                        + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                                count = count + 5;
                                break;
                            } else if (args[count].equals(args[count + 1]) && args[count + 2].equals(args[count + 3])) {
                                figs[i] = new Prostokat(Float.parseFloat(args[count]), Float.parseFloat(args[count + 2]));
                                System.out.print("A rectangle of sides " + args[count] + ", " + args[count + 2] +
                                        ": Circumference = " + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                                count = count + 5;
                                break;
                            } else {
                                System.out.print("\"" + args_0_c[i] + "\" with arguments: " + args[count] + ", " +
                                        args[count + 1] + ", " + args[count + 2] + ", " + args[count + 3] + ", " +
                                        args[count + 4] + ": is not a supported figure!");
                                return;
                            }

                        } else if (args[count].equals(args[count + 1]) && args[count + 1].equals(args[count + 2]) &&
                                args[count + 2].equals(args[count + 3]) && Float.parseFloat(args[count + 4]) < 90 &&
                                Float.parseFloat(args[count + 4]) > 0) {

                            figs[i] = new Romb(Float.parseFloat(args[count]), Float.parseFloat(args[count + 4]));
                            System.out.print("A rhombus of side " + args[count] + " and acute angle " + args[count + 4] +
                                    ": Circumference = " + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                            count = count + 5;
                            break;
                        } else {
                            System.out.print("\"" + args_0_c[i] + "\" with arguments: " + args[count] + ", " +
                                    args[count + 1] + ", " + args[count + 2] + ", " + args[count + 3] + ", " +
                                    args[count + 4] + ": is not a supported figure!");
                            return;
                        }

                    case 'p':
                        figs[i] = new Pieciakat(Float.parseFloat(args[count]));
                        System.out.print("A regular pentagon of side " + args[count] + ": Circumference = "
                                + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                        count++;
                        break;

                    case 's':
                        figs[i] = new Szesciokat(Float.parseFloat(args[count]));
                        System.out.print("A regular hexagon of side " + args[count] + ": Circumference = "
                                + figs[i].circumference() + ", area = " + figs[i].area() + "\n");
                        count++;
                        break;

                    default:
                        System.out.print("\"" + args_0_c[i] + "\"" + ": is not a recognizable figure!");
                }
            }
        }
        catch(NumberFormatException a){
            System.out.print("There is a problem with at least one of your arguments, please correct it.");
        }
        catch(ArrayIndexOutOfBoundsException b){
            System.out.print("Not enough arguments were given!");
        }
    }
}

abstract class Figura{
    public abstract double circumference();
    public abstract double area();
}

abstract class Czworokat extends Figura{
    public double circumference;
    public double area;
}

class Okrag extends Figura{
    double r;
    Okrag (double radius){
        r = radius;
    }
    public double circumference(){
        return 2*3.14159*r;
    }
    public double area(){
        return 3.14159*Math.pow(r,2);
    }
}

class Pieciakat extends Figura{
    double s;
    Pieciakat (double side) {
        s = side;
    }
    public double circumference(){
        return 5*s;
    }
    public double area(){
        return 5*s*s*(0.8090/(4*0.5878));
    }
}

class Szesciokat extends Figura{
    double s;
    Szesciokat (double side) {
        s = side;
    }
    public double circumference(){
        return 6*s;
    }
    public double area(){
        return 3*s*s*0.8660;
    }
}

class Kwadrat extends Czworokat{
    double s;
    Kwadrat (double side) {
        s = side;
    }
    public double circumference(){
        return 4*s;
    }
    public double area(){
        return s*s;
    }
}

class Prostokat extends Czworokat{
    double s1,s2;
    Prostokat (double side1, double side2) {
        s1 = side1;
        s2 = side2;
    }
    public double circumference(){
        return 2*s1+2*s2;
    }
    public double area(){
        return s1*s2;
    }
}

class Romb extends Czworokat{
    double s, a;
    Romb (double side, double angle) {
        s = side;
        a = Math.toRadians(angle);
    }
    public double circumference(){
        return 4*s;
    }
    public double area(){
        return s*s*Math.sin(a);
    }
}