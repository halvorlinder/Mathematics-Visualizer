package math;

import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Utils {
    /**
     * Returns the greatest common factor of two integers
     */
    public static int gcd(int a, int b){
        if(b==0){
            return a;
        }
        return gcd(b, a%b);
    }

    /**
     * Returns the least common multiple of two integers
     */
    public static int lcm(int a, int b){
        return a*b/gcd(a,b);
    }

    /**
     * Returns true if a double is a whole number, else false
     */
    public static boolean isWhole(double n){
        return (int)Math.floor(n)==(int)n;
    }

    /**
     * Returns n choose r
     */
    public double choose(int n, int r){
        if(r > n)
            return -1;
        return (double) factorial(n) / (double) (factorial(r) * factorial(n-r));
    }

    /**
     * Returns n!
     */
    public int factorial(int n){
        int ans = 1;
        for(int i = n; i >1; i--)
            ans *= i;

        return ans;
    }

    /**
     * ??
     */
    public double sum(int start, int end, IntToDoubleFunction function){
        return IntStream.rangeClosed(start, end).mapToDouble(i -> function.applyAsDouble(i)).sum();
    }

    /**
     * ??
     */
    public int sum(int start, int end, IntUnaryOperator function){
        return IntStream.rangeClosed(start, end).map(i -> function.applyAsInt(i)).sum();
    }

    /**
     * Returns the logarithm base 2 of a double
     */
    public static double log2(double num){
        return Math.log(num) / Math.log(2);
    }
}
