public class MainApp {
    public static void main(String[] args) {
        System.out.println(recursiveFactorial(5));
    }

    private static long recursiveFactorial(int number) {
        return (number > 1) ? recursiveFactorial(number - 1) * number : number;
    }
}
