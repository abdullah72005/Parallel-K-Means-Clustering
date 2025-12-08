import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class DataSetMaker {

    private static final double RADIUS = 30.0;
    private static final String CSV_HEADER = "id,x,y,label";

    public static void generateCSV(
            String filename,
            int K,
            int N,
            double sigma,
            long seed) throws IOException {

        Random random = new Random(seed);

        // Calculate offset to ensure all values are positive
        double offset = RADIUS + (3 * sigma);

        // Generate K cluster centers on a circle of radius RADIUS
        double[] centerX = new double[K];
        double[] centerY = new double[K];

        for (int i = 0; i < K; i++) {
            double angle = 2 * Math.PI * i / K;
            centerX[i] = RADIUS * Math.cos(angle);
            centerY[i] = RADIUS * Math.sin(angle);
        }

        // Write dataset to CSV file
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            // Write header
            writer.write(CSV_HEADER);
            writer.newLine();

            int pointId = 0;
            int remainingPoints = N;

            // Generate points for each cluster
            for (int i = 0; i < K; i++) {
                // Random cluster size, but ensure at least 1 point per cluster
                // and save enough points for remaining clusters
                int minPoints = 1;
                int maxPoints = remainingPoints - (K - i - 1);  // Leave at least 1 for each remaining cluster
                int clusterSize;
                
                if (i == K - 1) {
                    // Last cluster gets all remaining points
                    clusterSize = remainingPoints;
                } else {
                    // Random size between min and max
                    clusterSize = random.nextInt(maxPoints - minPoints + 1) + minPoints;
                }
                
                remainingPoints -= clusterSize;

                for (int j = 0; j < clusterSize; j++) {
                    // Sample from Gaussian distribution around cluster center
                    double x = centerX[i] + random.nextGaussian() * sigma + offset;
                    double y = centerY[i] + random.nextGaussian() * sigma + offset;

                    // Format and write CSV row
                    String line = String.format("%d,%.2f,%.2f,%d", pointId, x, y, i);
                    writer.write(line);
                    writer.newLine();

                    pointId++;
                }
            }
        }
    }
}
