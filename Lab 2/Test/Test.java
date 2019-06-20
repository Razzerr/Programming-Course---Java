public class Test{
    public static void main(String[] args){
        try {
            int n = Integer.parseInt(args[0]);
            //int[] tab = new int[n]; //For testing if the number provided is negative
            if (n<0) {
                System.out.println("'" + args[0] + "'" + " is not a natural number!");
                return;
            }
            WierszTrojkataPascala verse = new WierszTrojkataPascala(n);
                for (int i = 1; i < args.length; i++) {
                    try {
                        int j = Integer.parseInt(args[i]);
                        System.out.println(args[i] + "->" + verse.wspolczynnik(j));
                    } catch(NumberFormatException a){
                        System.out.println("'" + args[i] + "'" + " is not a natural number!");
                        continue;
                    } catch(IndexOutOfBoundsException ex){
                        System.out.println("'" + args[i] + "'" + " is out of bounds!");
                        continue;
                    }
                }
        } catch (NumberFormatException b) {
            System.out.println("'" + args[0] + "'" + " is not a natural number!");
            return;
        }
    }
}

class WierszTrojkataPascala {
    private int[] pascal_verse;
    WierszTrojkataPascala(int n) {
        pascal_verse = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            pascal_verse[i] = pv(n, i);
        }
    }

    private int pv(int n, int k){
        if (n==k || k==0)
            return 1;
        return pv(n-1, k-1) + pv(n-1, k);
    }

    int wspolczynnik(int m){
        return pascal_verse[m];
    }

}
