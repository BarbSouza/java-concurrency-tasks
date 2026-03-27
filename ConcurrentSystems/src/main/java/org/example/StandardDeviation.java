package org.example;

import java.sql.SQLOutput;

public class StandardDeviation {

    public class SumThread extends Thread {
        private double[] data;
        private int start;
        private int end;
        private double sum;
        double partialSum = 0;

        public SumThread(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " calculating sum from index " + start + " to " + (end - 1));
            sum = 0;
            for (int i = start; i < end; i++) {
                sum += data[i];
            }
        }

        public double getSum() {
            return sum;
        }
    }

}