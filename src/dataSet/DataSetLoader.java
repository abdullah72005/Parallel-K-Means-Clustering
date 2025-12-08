package dataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataSetLoader {

    public static List<Point> loadCSV(String filename) throws IOException {
        List<Point> points = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip header row
                if (lineNumber == 1) {
                    continue;
                }

                try {
                    Point point = Point.parsePoint(line);
                    points.add(point);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            String.format("Error parsing line %d: %s. Content: %s", lineNumber, e.getMessage(), line),
                            e);
                }
            }
        }

        return points;
    }

    public static int getK(List<Point> points) {
        Set<Integer> uniqueLabels = new HashSet<>();
        for (Point point : points) {
            uniqueLabels.add(point.getLabel());
        }
        return uniqueLabels.size();
    }
}
