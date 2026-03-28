package org.example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class provides methods for splitting input data into two 10x10 matrices,
 * performing matrix multiplication, and printing the resulting matrix.
 */
public class MatrixMultiplication {

    // Method to split the input data into two 10x10 matrices
    public static double[][][] splitMatrices(double[] data) {
        // Initialize two 10x10 matrices
        double[][] matrixA = new double[10][10];
        double[][] matrixB = new double[10][10];

        // Fill the first matrix (matrixA) with the first 100 values from the input data
        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrixA[i][j] = data[index++];
            }
        }
        // Fill the second matrix (matrixB) with the next 100 values from the input data
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrixB[i][j] = data[index++];
            }
        }

        return new double[][][]{matrixA, matrixB};
    }

    // Runnable task for calculating a portion of the resulting matrix
    public class RowTask implements Runnable {
        private double[][] A;
        private double[][] B;
        private double[][] result;
        private int startRow;
        private int endRow;

        static AtomicInteger threadCounter = new AtomicInteger(0);
        int threadID;

        RowTask(double[][] A, double[][] B, double[][] result, int startRow, int endRow) {
            this.A = A;
            this.B = B;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
            this.threadID = threadCounter.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println("Thread " + threadID + " calculating rows from " + startRow + " to " + (endRow - 1));
            int size = A.length;
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    double sum = 0;
                    for (int k = 0; k < size; k++) {
                        sum += A[i][k] * B[k][j];
                    }
                    result[i][j] = sum;
                }
            }
                System.out.println("Thread " + threadID + " finished calculating rows from " + startRow + " to " + (endRow - 1));
        }
    }

    // Method to perform matrix multiplication
    public static double[][] multiply(double[][] A, double[][] B) {
        int size = A.length;
        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double sum = 0;
                for (int k = 0; k < size; k++) {
                    sum += A[i][k] * B[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    // Method to print a matrix
    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.1f ", value);
            }
            System.out.println();
        }
    }

}