package org.example;


import java.util.concurrent.atomic.AtomicInteger;

/** * This class calculates the standard deviation of a dataset using multiple threads.
 * It uses two types of threads: SumThread to calculate the total sum for mean calculation, and VarianceThread to calculate the sum of squared differences for variance calculation.
 * The main method, calculateStandardDeviation, orchestrates the creation and execution of these threads and combines their results to return the final standard deviation.
 */

public class StandardDeviation {

    // Runnable class to calculate the sum of a portion of the data
    static class SumThread implements Runnable {
        private double[] data;
        private int start;
        private int end;
        double partialSum = 0; // instance variable to store the sum for this thread's portion of the data
        /*
        Each thread will calculate the sum of a portion of the data and store it in partialSum
        This avoids the need for synchronization since each thread writes to its own variable
        The main thread will later read these partial sums to calculate the total sum and mean
        This design follows the CountThreadRefactor pattern where each thread is responsible for a specific portion of the data and stores its result in an instance variable
         */

        static AtomicInteger threadCounter = new AtomicInteger(0);
        int threadID;


        public SumThread(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.threadID = threadCounter.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println("SumThread " + threadID + " calculating sum from index " + start + " to " + (end - 1));
            for (int i = start; i < end; i++) {
                partialSum += data[i];
            }
            System.out.println("SumThread " + threadID + " finished calculating sum. Partial sum: " + partialSum);
        }
    }

    // Runnable class to calculate the sum of squared differences for a portion of the data
    static class VarianceThread implements Runnable {
        private double[] data;
        private int start;
        private int end;
        private double mean;
        double partialSum = 0; // instance variable to store the sum of squared differences for this thread's portion of the data

        static AtomicInteger threadCounter = new AtomicInteger(0);
        int threadID;

        public VarianceThread(double[] data, int start, int end, double mean) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.mean = mean;
            this.threadID = threadCounter.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println("VarianceThread " + threadID + " calculating variance from index " + start + " to " + (end - 1));
            for (int i = start; i < end; i++) {
                double diff = data[i] - mean;
                partialSum += diff * diff;
            }
            System.out.println("VarianceThread " + threadID + " finished calculating variance. Partial sum of squared differences: " + partialSum);
        }
    }
    // Main method to calculate standard deviation using multiple threads
    /*
    Step 1: Calculate the mean using multiple threads (SumThread)
    Step 2: Calculate the variance using multiple threads (VarianceThread)
    Step 3: Return the standard deviation as sqrt(variance / n)
    */

    public static double calculateStandardDeviation(double[] data, int numThreads) throws InterruptedException  {
        /*
        Determine the number of elements in the data array (n).
        Create an array of SumThread and Thread objects to calculate the sum in parallel.
        Calculate the chunk size for each thread to process.
        Start each thread to calculate the partial sum for its assigned chunk of data.
        Wait for all threads to finish using join().
        Sum up the partial sums from all threads to get the total sum and calculate the mean.
         */
        int n = data.length;
        if (n == 0) return 0.0;

        // Step 1: Calculate mean using threads
        SumThread[] sumThreads = new SumThread[numThreads];
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (n + numThreads - 1) / numThreads; // ceiling division to handle cases where n is not perfectly divisible by numThreads

        // Create and start threads to calculate partial sums
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, n);
            sumThreads[i] = new SumThread(data, start, end);
            threads[i] = new Thread(sumThreads[i]);
            threads[i].start();
        }

        // Wait for all sum threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Calculate total sum and mean
        double totalSum = 0;
        for (SumThread sumThread : sumThreads) {
            totalSum += sumThread.partialSum;
        }
        double mean = totalSum / n;
        System.out.println("Total sum: " + totalSum + ", Mean: " + mean);

        // Step 2: Calculate variance using threads
        VarianceThread[] varianceThreads = new VarianceThread[numThreads];

        // Create and start threads to calculate partial sums of squared differences
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, n);
            varianceThreads[i] = new VarianceThread(data, start, end, mean);
            threads[i] = new Thread(varianceThreads[i]);
            threads[i].start();
        }

        // Wait for all variance threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Calculate total sum of squared differences
        double totalSquaredDiff = 0;
        for (VarianceThread varianceThread : varianceThreads) {
            totalSquaredDiff += varianceThread.partialSum;
        }
        // Step 3: Return standard deviation
        return Math.sqrt(totalSquaredDiff / n);
    }
}