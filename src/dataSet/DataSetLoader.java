package dataSet;

import main.Point ;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataSetLoader {

    public static Map<Integer, Point> loadCSV(String filename) throws IOException {
        Map<Integer, Point> pointMap = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String line;
            int lineNumber = 0;
            int pointId = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip header row
                if (lineNumber == 1) {
                    continue;
                }

                try {
                    Point point = Point.parsePoint(line);
                    pointMap.put(pointId++, point);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            String.format("Error parsing line %d: %s. Content: %s", lineNumber, e.getMessage(), line),
                            e);
                }
            }
        }

        return pointMap;
    }

    public static int getK(Map<Integer, Point> pointMap) {
        Set<Integer> uniqueLabels = new HashSet<>();
        for (Point point : pointMap.values()) {
            uniqueLabels.add(point.getLabel());
        }
        return uniqueLabels.size();
    }
}
