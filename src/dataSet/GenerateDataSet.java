package dataSet;

import java.io.IOException;

public class GenerateDataSet {
    public static void main(String[] args) throws IOException {
        DataSetMaker.generateCSV("new_medium.csv", 5, 50000, 7, 676767);
    }
}
