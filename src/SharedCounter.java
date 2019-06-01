import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SharedCounter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("How many threads to use?");
        int threads_count = in.nextInt();
        List<Thread> threads = new ArrayList<>();
        List<MyRunnable> runnables = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < threads_count; i++) {
            runnables.add(new MyRunnable(num));
        }

        for (MyRunnable runnable : runnables)
            threads.add(new Thread(runnable));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (MyRunnable runnable : runnables) {
            System.out.println(runnable.getNum());
        }
    }

    private static class MyRunnable implements Runnable {
        private static int i;

        public MyRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            i++;
        }

        public int getNum() {
            return i;
        }
    }

}





