import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

public class MaxFinder {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("How many threads to use?");
        int threads_count = in.nextInt();
        List<Thread> threads = new ArrayList<>();
        List<MyRunnable> runnables = new ArrayList<>();
        final int SIZE = 1000000000;
      //  int[] array = new int[SIZE];

        for (int i = 0; i < threads_count; i++) {
            runnables.add(new MyRunnable(new int[SIZE / threads_count], i * SIZE / threads_count, (i + 1) * SIZE / threads_count));
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

        int arr_max = 0;
        for (MyRunnable runnable : runnables) {
            if (runnable.getMax() > arr_max)
                arr_max = runnable.getMax();

            System.out.println(runnable.getMax());
        }
        System.out.println();
        System.out.println(arr_max);
    }

    private static class MyRunnable implements Runnable {
        private int[] arr;
        private int count1;
        private int count2;
        private int max;
        private Random rand = new Random();

        public MyRunnable(int[] arr, int count1, int count2) {
            this.arr = arr;
            this.count1 = count1;
            this.count2 = count2;
        }

        @Override
        public void run() {
            for (int i = 0; i < count2 - count1; i++) {
                arr[i] = rand.nextInt(1000000);
                if (arr[i] > max)
                    max = arr[i];
            }
        }
        public int getMax() {
            return max;
        }
    }

}



