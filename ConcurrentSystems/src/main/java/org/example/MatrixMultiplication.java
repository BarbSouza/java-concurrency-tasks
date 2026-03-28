package org.example;

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
}