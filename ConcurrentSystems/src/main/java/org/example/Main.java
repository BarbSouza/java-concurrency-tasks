package org.example;

import java.io.IOException;

public class Main {
    // Main method to execute the tasks
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("--- Concurrent Systems Tasks ---");
        // Task 1: Load Data
        double[] number = CSVReader.readCSV("data.csv");
        System.out.println("Data loaded successfully.");
        System.out.println("Total values: " + number.length);

        // Task 2: Standard Deviation
        System.out.println("\n--- Standard Deviation ---");
        int numThreads = 5;
        double stdDev = StandardDeviation.calculateStandardDeviation(number, numThreads);
        System.out.println("Standard Deviation: " + stdDev + " calculated using " + numThreads + " threads.");

        // Task 3: Matrix Multiplication
        System.out.println("\n--- Matrix Multiplication ---");
        double[][][] matrices = MatrixMultiplication.splitMatrices(number);
        double[][] A = matrices[0];
        double[][] B = matrices[1];

        System.out.println("Matrix A:");
        MatrixMultiplication.printMatrix(A);
        System.out.println("Matrix B:");
        MatrixMultiplication.printMatrix(B);

        double[][] result = MatrixMultiplication.multiply(A, B, numThreads);
        System.out.println("Result of A x B:");
        MatrixMultiplication.printMatrix(result);
        System.out.println("\n------------------");

        // Task 4: Merge Sort
        System.out.println("\n--- Merge Sort ---");
        MergeSort.threadCount.set(0);

        System.out.println("Data before sorting:");
        for (double value : number) {
            System.out.printf("%8.1f ", value);
        }
        System.out.println("\n");
        MergeSort.sort(number);
        System.out.println("Total parallel tasks forked: " + MergeSort.threadCount.get());
        System.out.println("Sorted data (largest to smallest):");
        for (double value : number) {
            System.out.printf("%8.1f ", value);
        }
        System.out.println("\n------------------");
    }
}