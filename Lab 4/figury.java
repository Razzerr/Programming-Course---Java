public class figury{
    public static void main(String[] args){
        int q = args.length;
        Figura[] figs = new Figura[q];

        for (int i = 0; i<q; i++){
            char arg[] = args[i].toCharArray();
            switch (arg[0]) {
                case 'c':
                    figs[i] = new Circle(Character.getNumericValue(arg[1]));
                    System.out.print("A circle of radius " + arg[1] + ": Circumference = "
                            + figs[i].perimeter() + ", area = " + figs[i].area() + "\n");
                    break;

                case 'r':
                    figs[i] = new Regular(Character.getNumericValue(arg[1]), Character.getNumericValue(arg[2]));
                    System.out.print("A regular " + arg[1] + "-gon with sides equal " + arg[2] + ": Circumference = "
                            + figs[i].perimeter() + ", area = " + figs[i].area() + "\n");
                    break;

                default:
                    System.out.print("Not supported figure!");
            }
        }
    }
}

abstract class Figura {
    public abstract double perimeter();
    public abstract double area();
}

class Circle extends Figura{
    double r;

    Circle (double rad){
        r=rad;
    }
    public double perimeter(){
        return 2*3.14159*r;
    }
    public double area(){
        return 3.14159*r*r;
    }
}

class Regular extends Figura{
    double a, n;

    Regular(double n_sides, double side){
        a=side;
        n=n_sides;
    }
    public double perimeter(){
        return a*n;
    }
    public double area(){
        return n*0.25*a*(Math.sin((3.14159/2)-(3.14159/n))/Math.sin(3.14159/n));
    }
}

class Rhombus extends Figura{

}

class Rectangle extends Figura{

}