package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ParallelisedCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String expression = "( ( 3 + 4 * 3 ) * 2 ) - ( -1 + 3 * 3 )";
        Scanner in = new Scanner(System.in);
        System.out.println("How many threads to use?");
        int threads_count = in.nextInt();
        List<Thread> threads = new ArrayList<>();
        List<MyRunnable> runnables = new ArrayList<>();
        System.out.println("print expression");
        String expression = scanner.nextLine();

        for (int i = 0; i < threads_count; i++) {
            runnables.add(new MyRunnable(expression, i * expression.length() / threads_count, (i + 1) * expression.length() / threads_count));
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

        for (MyRunnable runnable:runnables) {
            System.out.println(runnable.getResult());
        }
    }

    private static class MyRunnable implements Runnable {
        private String str;
        private int count1;
        private int count2;
        private String result;

        public MyRunnable(String str, int count1, int count2) {
            this.str = str;
            this.count1 = count1;
            this.count2 = count2;
        }

        @Override
        public void run() {
            result = Calculator.calculateExpression(str.substring(count1, count2));

        }

        public String getResult() {
            return result;
        }
    }

}
