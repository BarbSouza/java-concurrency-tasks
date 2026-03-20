package org.example;

public class StandardDeviation {
    public static double calculateStandardDeviation(double[] data) {
        double mean = calculateMean(data);
        double sumSquaredDifferences = 0.0;

        for (double value : data) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }

        return Math.sqrt(sumSquaredDifferences / data.length);
    }

    private static double calculateMean(double[] data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }

}
