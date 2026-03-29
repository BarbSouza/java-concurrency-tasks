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

    private static final int THRESHOLD = 25;

    static AtomicInteger threadCount = new AtomicInteger(0);

    static class MergeSortTask extends RecursiveAction {
        private double[] data;
        private int left;
        private int right;

        MergeSortTask(double[] data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if ((right - left) <= THRESHOLD) {
                System.out.println("Thread " + threadCount.incrementAndGet() + " sorting from index " + left + " to " + right);
                sequentialSort(data, left, right);
            } else {
                int mid = (left + right) / 2;
                MergeSortTask leftTask = new MergeSortTask(data, left, mid);
                MergeSortTask rightTask = new MergeSortTask(data, mid + 1, right);
                System.out.println("Splitting from index " + left + " to " + right + " into " + left + " at " + mid);

                leftTask.fork();
                rightTask.compute();
                leftTask.join();
                merge(data, left, mid, right);

            }
        }
    }

    private static void sequentialSort(double[] data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sequentialSort(data, left, mid);
            sequentialSort(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }

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

}