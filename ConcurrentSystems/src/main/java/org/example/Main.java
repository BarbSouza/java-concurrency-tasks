package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        double[] number = CSVReader.readCSV("C:\\Users\\barba\\Desktop\\GitHubRepositories\\concurrent-systems-ca1-Barbs28\\ConcurrentSystems\\data.csv");
        System.out.println("First value: " + number[0]);
    }
}