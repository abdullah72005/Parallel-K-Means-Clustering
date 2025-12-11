import dataSet.DataSetMaker;
import java.io.IOException;

public class Example {
    public static void main(String[] args) {
        //Generating a dataset
        String filename = "dataSet/great_medium.csv";
        int K = 5;
        int N = 5000;
        
        //sigma should be between 2 and 3 to ensure that the upperbound is 100
        double sigma = 7;
        long seed = 20;

        try {
            DataSetMaker.generateCSV(filename, K, N, sigma, seed);
            System.out.println("Dataset generated successfully: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}