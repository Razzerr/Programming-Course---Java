import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class figures {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> arguments = rewrite(args);

        ArrayList<Figury.OneArg> figs1 = new ArrayList<Figury.OneArg>();
        ArrayList<Figury.TwoArgs> figs2 = new ArrayList<Figury.TwoArgs>();

        for (ArrayList<String> a : arguments) {
            try {
                if (a.size() > 1 && a.size() < 4) {
                    switch (a.get(0)) {
                        case "o":
                            if (a.size() < 3) {
                                figs1.add(0, Figury.OneArg.CIRCLE);
                                Figury.OneArg tempo = figs1.get(0);
                                tempo.value(Double.parseDouble(a.get(1)));

                                System.out.print("Circle of radius " + a.get(1) + ": Circumference = " +
                                        tempo.circumference() + ", Area = " + tempo.area() + "\n");
                            } else {
                                System.out.print(a + ": Too many or too few arguments for this input!\n");
                            }
                            break;

                        case "p":
                            figs2.add(0, Figury.TwoArgs.RECTANGLE);
                            Figury.TwoArgs tempp = figs2.get(0);
                            tempp.value(Double.parseDouble(a.get(1)), Double.parseDouble(a.get(2)));

                            System.out.print("Rectangle of sides " + a.get(1) + ", " + a.get(2) + ": Perimeter = " +
                                    tempp.perimeter() + ", Area = " + tempp.area() + "\n");
                            break;

                        case "r":
                            figs2.add(0, Figury.TwoArgs.RHOMBUS);
                            Figury.TwoArgs tempr = figs2.get(0);
                            tempr.value(Double.parseDouble(a.get(1)), Double.parseDouble(a.get(2)));

                            System.out.print("Rhombus of sides " + a.get(1) + ", and an acute angle of " + a.get(2) +
                                    ": Perimeter = " + tempr.perimeter() + ", Area = " + tempr.area() + "\n");
                            break;

                        case "w":
                            figs2.add(0, Figury.TwoArgs.REG_POLY);
                            Figury.TwoArgs tempw = figs2.get(0);
                            tempw.value(Double.parseDouble(a.get(1)), Double.parseDouble(a.get(2)));

                            System.out.print(a.get(1) + "-gon of sides " + a.get(2) + ": Perimeter = " +
                                    tempw.perimeter() + ", Area = " + tempw.area() + "\n");
                            break;

                        default:
                            System.out.print(a + ": Not a recognizable figure!\n");
                    }
                } else {
                    System.out.print(a + ": Too many or too few arguments for this input!\n");
                }
            } catch (NumberFormatException b) {
                System.out.print(a + ": One of the arguments is invalid!\n");
            } catch (IndexOutOfBoundsException c) {
                System.out.print(a + ": Not enough rguments for this input!\n");
            }
        }
    }

    public static ArrayList<ArrayList<String>> rewrite(String[] args) {
        ArrayList<ArrayList<String>> arguments = new ArrayList<ArrayList<String>>();
        ArrayList<String> transit = new ArrayList<String>();

        Pattern letters = Pattern.compile("[a-z]");
        int i = 1;

        for (String a : args) {
            if (letters.matcher(a).find()) {
                if (transit.size() == 0) {
                    transit.add(a);
                } else {
                    arguments.add(0, transit);
                    transit = new ArrayList<String>();
                    transit.add(a);
                }
            } else {
                transit.add(a);
                if (i == args.length) {
                    arguments.add(0, transit);
                }
            }
            i++;
        }

        return arguments;
    }
}

class Figury {
    public enum OneArg {
        CIRCLE;

        double r;

        void value(double rad) {
            this.r = rad;
        }

        double area() {
            switch (this) {
                case CIRCLE:
                    return 3.14159 * r * r;
            }
            return 0;
        }

        double circumference() {
            switch (this) {
                case CIRCLE:
                    return 2 * 3.14159 * r;
            }
            return 0;
        }
    }

    public enum TwoArgs {
        RECTANGLE,
        RHOMBUS,
        REG_POLY;

        double arg1, arg2;

        void value(double a, double b) {
            this.arg1 = a;
            this.arg2 = b;
        }

        double area() {
            switch (this) {
                case RECTANGLE:
                    return arg1 * arg2;
                case RHOMBUS:
                    return Math.abs(arg1 * arg1 * Math.sin(arg2));
                case REG_POLY:
                    return arg1 * 0.25 * arg2 * (Math.sin((3.14159 / 2) - (3.14159 / arg1)) / Math.sin(3.14159 / arg1));
            }
            return 0;
        }

        double perimeter() {
            switch (this) {
                case RECTANGLE:
                    return 2 * arg1 + 2 * arg2;
                case RHOMBUS:
                    return 4 * arg1;
                case REG_POLY:
                    return arg1 * arg2;
            }
            return 0;
        }
    }
}