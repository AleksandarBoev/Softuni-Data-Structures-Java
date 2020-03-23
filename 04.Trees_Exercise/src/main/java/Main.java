public class Main {
    public static void main(String[] args) {
//        boolean breakRecursion = false; //TODO
//        recursiveMethod(5);
    }

    public static void recursiveMethod(int num) {
        if (num <= 0) {
            return;
        }

//        if (breakRecursion)

        recursiveMethod(num - 1);
        System.out.println(num);
    }
}
