import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ReverseText {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("How many threads to use?");
        int threads_count = in.nextInt();
        List<Thread> threads = new ArrayList<>();
        List<MyRunnable> runnables = new ArrayList<>();
        System.out.println("print String");
        //final int SIZE = 1000000000;
        String str = in.next();
        String reverse = "";

        for (int i = 0; i < threads_count; i++) {
            runnables.add(new MyRunnable(str, i * str.length() / threads_count, (i + 1) * str.length() / threads_count));
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

        for ( int i = runnables.size()-1;i>=0; i--) {
            reverse+=runnables.get(i).getReversed();
        }
        System.out.println("reversed string -> " + reverse);
    }

    private static class MyRunnable implements Runnable {
        private String str;
        private int count1;
        private int count2;
        private String rev = "";
        public MyRunnable(String str, int count1, int count2) {
            this.str = str;
            this.count1 = count1;
            this.count2 = count2;
        }

        @Override
        public void run() {
            for (int i = count2-1; i >= count1; i--) {
                rev += str.charAt(i);
            }
        }

        public String getReversed() {
            return rev;
        }
    }

}
