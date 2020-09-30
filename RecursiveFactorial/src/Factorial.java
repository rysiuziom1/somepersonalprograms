public class Factorial {
    public static long recursiveFactorial(int number) {
        return (number > 1) ? recursiveFactorial(number - 1) * number : number;
    }
}
