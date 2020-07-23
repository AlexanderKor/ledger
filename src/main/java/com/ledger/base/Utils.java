package com.ledger.base;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Utils {

    public static void readCsvFromFile(int rowVerticalNumber, int rowHorizontalNumber, String pathToCsvFile) throws IOException {
        File csvData = new File(pathToCsvFile);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
        System.out.println(parser.getRecords().get(rowVerticalNumber).get(rowHorizontalNumber));
        for (CSVRecord csvRecord : parser) {
            System.out.println(csvRecord);
        }
    }

    public static String getValueFromCsvFile(int rowVerticalNumber, int rowHorizontalNumber, String pathToCsvFile) throws IOException {
        File csvData = new File(pathToCsvFile);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
        return parser.getRecords().get(rowVerticalNumber).get(rowHorizontalNumber);
    }
}
