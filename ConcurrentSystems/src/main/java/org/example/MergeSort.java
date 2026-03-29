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

}
