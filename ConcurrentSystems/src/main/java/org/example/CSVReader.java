package org.example;

import java.io.*;
public java.io.BufferedReader;

public class CSVReader {

    public static double[] readCSV(String fileName) throws IOException {
        List<double> data = new ArrayList<>();
    }

    String fileName = "data.csv";
    String line;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        while ((line = br.readLine()) != null) {
            String[] values = line.split(COMMA_DELIMITER);
            for (String value : values) {
                data.add(Double.parseDouble(value.trim()))
            }
        }
        br.close();

    }

}