package org.example;

import java.sql.SQLOutput;

public class StandardDeviation {

    // Runnable class to calculate the sum of a portion of the data
    public class SumThread implements Runnable {
        private double[] data;
        private int start;
        private int end;
        double partialSum = 0;

        public SumThread(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " calculating sum from index " + start + " to " + (end - 1));
            for (int i = start; i < end; i++) {
                partialSum += data[i];
            }
        }
    }

}