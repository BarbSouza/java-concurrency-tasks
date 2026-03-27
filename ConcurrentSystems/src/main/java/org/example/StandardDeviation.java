package org.example;


public class StandardDeviation {

    // Runnable class to calculate the sum of a portion of the data
    static class SumThread implements Runnable {
        private double[] data;
        private int start;
        private int end;
        double partialSum = 0; // instance variable

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

        return totalSum;
    }
}