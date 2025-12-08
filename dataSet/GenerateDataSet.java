import java.io.IOException;

public class GenerateDataSet {

    public static void main(String[] args) {
        try {
            // Configuration parameters
            String filename = "kmeans_dataset.csv";
            int K = 4;              // Number of clusters
            int N = 1000;           // Total number of points
            double sigma = 3.0;     // Standard deviation (cluster spread)
            long seed = 10;         // Random seed for reproducibility

            // Generate the dataset
            DataSetMaker.generateCSV(filename, K, N, sigma, seed);
            
            System.out.println("\nDataset generated successfully!");
            System.out.println("File: " + filename);

        } catch (IOException e) {
            System.err.println("Error generating dataset: " + e.getMessage());
        }
    }
}
