package org.example;

import java.sql.SQLOutput;

public class StandardDeviation {

    // Runnable class to calculate the sum of a portion of the data
    static class SumThread implements Runnable {
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

    // Runnable class to calculate the sum of squared differences for a portion of the data
    static class VarianceThread implements Runnable {
        private double[] data;
        private int start;
        private int end;
        private double mean;
        double partialSum = 0;

        public VarianceThread(double[] data, int start, int end, double mean) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.mean = mean;
        }

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " calculating variance from index " + start + " to " + (end - 1));
            for (int i = start; i < end; i++) {
                double diff = data[i] - mean;
                partialSum += diff * diff;
            }
        }
    }
}