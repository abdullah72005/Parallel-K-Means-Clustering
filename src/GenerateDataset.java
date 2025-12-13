import dataSet.DataSetMaker;
import java.io.IOException;

public class GenerateDataset {
    public static void main(String[] args) {
        //Generating a dataset
        String filename = "dataSet/great_large.csv";
        int K = 3;
        int N = 1000;
        
        double sigma = 6;
        long seed = 693;

        try {
            DataSetMaker.generateCSV(filename, K, N, sigma, seed);
            System.out.println("Dataset generated successfully: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}