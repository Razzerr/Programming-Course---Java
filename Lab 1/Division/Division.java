public class Division { 
	public static void main(String[] args) { 
		int n = 0;
		for (int i = 0; i < args.length; i++){
			try { 
				n=Integer.parseInt(args[i]); 
				System.out.println("Number " + args[i] + " has the biggest divisor equal " + div(n));
			} catch (NumberFormatException ex) { 
				System.out.println(args[i] + " is not an integer!"); 
			}
		}
	} 
	public static int div(int n){
		if (n == 0){return 0;}
		if (n%2 == 0) {return n/2;}
		for (int i = (int)(n/2)+1; i>0; i--){
			if (n%i == 0){return i;}
		}
		return 0;
	}
}
