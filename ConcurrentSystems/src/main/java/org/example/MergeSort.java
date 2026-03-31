package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements the Merge Sort algorithm with a threshold for switching to a sequential sort.
 * Largest to smallest
 * It uses an AtomicInteger to count the number of threads created during the sorting process.
 */

public class MergeSort {
    // Threshold for switching to sequential sort
    private static final int THRESHOLD = 25;
    // AtomicInteger to keep track of the number of threads created during the sorting process
    static AtomicInteger threadCount = new AtomicInteger(0);

    // RecursiveAction is used for tasks that don't return a result (void)
    static class MergeSortTask extends RecursiveAction {
        private double[] data;
        private int left;
        private int right;

        MergeSortTask(double[] data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        // The compute method is where the sorting logic is implemented
        @Override
        protected void compute() {
            if ((right - left) <= THRESHOLD) {
                sequentialSort(data, left, right);
            } else {
                int mid = (left + right) / 2;
                MergeSortTask leftTask = new MergeSortTask(data, left, mid);
                MergeSortTask rightTask = new MergeSortTask(data, mid + 1, right);
                System.out.println("Splitting [" + left + " to " + right + "] → left:[" + left + " to " + mid + "] right:[" + (mid+1) + " to " + right + "]");

                threadCount.incrementAndGet();
                leftTask.fork();
                rightTask.compute();
                leftTask.join();
                merge(data, left, mid, right);

            }
        }
    }

    /**
     * This method performs a sequential merge sort on a portion of the array defined by the left and right indices.
     * @param data
     * @param left
     * @param right
     */
    private static void sequentialSort(double[] data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sequentialSort(data, left, mid);
            sequentialSort(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }

    /**
     * This method merges two sorted subarrays of data defined by the left, mid, and right indices.
     * @param data
     * @param left
     * @param mid
     * @param right
     */
    private static void merge(double[] data, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        double[] leftArray = new double[leftSize];
        double[] rightArray = new double[rightSize];

        for (int i = 0; i < leftSize; i++)
            leftArray[i] = data[left + i];
        for (int i = 0; i < rightSize; i++)
            rightArray[i] = data[mid + 1 + i];

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] >= rightArray[j]) {
                data[k] = leftArray[i];
                i++;
            } else {
                data[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            data[k++] = leftArray[i++];
        }
        while (j < rightSize) {
            data[k++] = rightArray[j++];
        }

    }

    /**
     * This method initializes the ForkJoinPool and starts the sorting process by invoking the MergeSortTask on the entire array.
     * @param data
     */
    public static void sort(double[] data) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSortTask(data, 0, data.length - 1));

    }

}