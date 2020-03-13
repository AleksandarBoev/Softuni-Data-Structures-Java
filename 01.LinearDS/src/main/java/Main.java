import implementations.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
