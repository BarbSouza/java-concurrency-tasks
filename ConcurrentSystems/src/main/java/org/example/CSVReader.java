package org.example;

import java.io.*;
import java.util.*;


public class CSVReader {

    public static double[] readCSV(String filePath) throws IOException {
        List<Double> data = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    data.add(Double.parseDouble(value.trim()));
                }
            }

            double[] result = new double[data.size()];
            for (int i = 0; i < data.size(); i++) {
                result[i] = data.get(i);

        }
            return result;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            throw e;
        }

    }
}