package org.fundacionjala.salesforce.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [SL] This class read a csv file.
 */
public final class CSVReader {

    /**
     * [SL] Constructor.
     */
    private CSVReader() {

    }

    /**
     * [SL] Gets all data of a csv file.
     *
     * @param filePath
     * @return a List of arrays with all data of the csv
     * @throws IOException
     */
    public static List<CSVRecord> getRecords(final String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader input = new InputStreamReader(inputStream);
        CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
        return csvParser.getRecords();
    }

    /**
     * [SL-MR] Gets information from a Csv file as List Of Maps.
     *
     * @param filePath to read information
     * @return List of Maps with file headers as keys
     * @throws IOException
     */
    public static List<Map<String, String>> getListOfMapsFromCsvFile(final String filePath) throws IOException {
        List<Map<String, String>> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = "";
            String[] keys = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                Map<String, String> container = new HashMap<>();
                String[] values = line.split(",");
                for (int i = 0; i < keys.length; i++) {
                    container.put(keys[i], values[i]);
                }
                content.add(container);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }
}
