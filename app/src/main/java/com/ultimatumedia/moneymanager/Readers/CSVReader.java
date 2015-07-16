package com.ultimatumedia.moneymanager.Readers;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 7/7/15.
 */
public class CSVReader {
    InputStream inputStream;

    public CSVReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<String[]> readCSVFile(Context context) {
        List<String[]> resultList = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        } catch(IOException ex) {
            Toast.makeText(context, "Error in reading CSV file:" + ex, Toast.LENGTH_SHORT).show();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Toast.makeText(context, "Error while closing input stream:" + e, Toast.LENGTH_SHORT).show();
            }
        }
        return resultList;
    }
}
