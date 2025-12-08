import dataSet.DataSetLoader;
import dataSet.DataSetMaker;
import dataSet.Point;
import java.io.IOException;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        try {
            //Generating a dataset
            String filename = "dataSet/example.csv";
            int K = 3;
            int N = 500;
            //sigma should be between 2 and 3 to ensure that the upperbound is 100
            double sigma = 2;
            long seed = 20;

            DataSetMaker.generateCSV(filename, K, N, sigma, seed);

            List<Point> points = DataSetLoader.loadCSV(filename);
            System.out.println("Loaded " + points.size() + " points");
            System.out.println();

            // Get K value
            int k = DataSetLoader.getK(points);
            System.out.println("K value (number of clusters): " + k);
            System.out.println();

            // Step 4: Print one point using getters
            if (!points.isEmpty()) {
                Point firstPoint = points.get(0);
                System.out.println("First point details:");
                System.out.println("  ID: " + firstPoint.getId());
                System.out.println("  X: " + firstPoint.getX());
                System.out.println("  Y: " + firstPoint.getY());
                System.out.println("  Label: " + firstPoint.getLabel());
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
