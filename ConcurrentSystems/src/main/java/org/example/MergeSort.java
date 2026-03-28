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
    }



}
