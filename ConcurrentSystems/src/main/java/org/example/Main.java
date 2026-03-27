package org.example;

import java.io.IOException;

public class Main {
    // Main method to execute the tasks
    public static void main(String[] args) throws IOException, InterruptedException {

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

        // Task 4: Merge Sort
        System.out.println("\n--- Merge Sort ---");
    }
}