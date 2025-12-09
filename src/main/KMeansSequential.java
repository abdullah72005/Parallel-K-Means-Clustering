package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KMeansSequential {
    private Map<Integer, Point> PointstoIdHash;
    private Map<Integer, Integer> pointsToCentroidHash = new HashMap<>();
    private KMeansConfig config;
    private Cluster[] clusters;

    public KMeansSequential(Map<Integer, Point> PointstoIdHash, KMeansConfig config) {
        this.PointstoIdHash = PointstoIdHash;
        this.config = config;
        this.clusters = new Cluster[config.k];
        initialize_centroids();
    }

    private void initialize_centroids() {
        for (int i = 0; i < this.config.k; i++) {
            double x = config.rng.nextDouble() * 100;
            double y = config.rng.nextDouble() * 100;
            clusters[i] = new Cluster(i, new Point(x, y));
        }
    }

    private void assign_points() {
        // Clear cluster assignments
        for (Cluster c : clusters) {
            c.setPoints(new ArrayList<>());
        }

        for (Point point : PointstoIdHash.values()){
            double closest_distance = Double.MAX_VALUE;
            Cluster closest_ref = null;

            for (Cluster cluster : clusters) {
                double distance = distance(point, cluster.getCentroid());
                if (distance < closest_distance) {
                    closest_distance = distance;
                    closest_ref = cluster;
                }
            }
            
            try {
                if (closest_ref == null) {
                    System.out.println("Error: No cluster found for point " + point.getId());
                    continue;
                }
                pointsToCentroidHash.put(point.getId(), closest_ref.getId());
                closest_ref.addPoint(point.getId());
            }
            catch (NullPointerException e){
                System.out.println("Error assigning point: " + e.getMessage());
            }
        }
    }
    private void update_centroids(){
        for (Cluster cluster : clusters){
            
            // Case 1: cluster is empty → skip update (keep current centroid)
            if (cluster.getPoints().isEmpty()) {
                continue;
            }

            // Case 2: compute mean normally
            double sumX = 0;
            double sumY = 0;
            for (Integer pointId : cluster.getPoints()) {
                sumX += PointstoIdHash.get(pointId).getX();
                sumY += PointstoIdHash.get(pointId).getY();
            }

            double meanX = sumX / cluster.getPoints().size();
            double meanY = sumY / cluster.getPoints().size();

            cluster.setCentroid(new Point(meanX, meanY));
        }
    }

    public void run(){
        initialize_centroids();
        for (int i = 0; i < config.maxIterations; i++) {
            assign_points();
            update_centroids();
        }
        assign_points(); // Final assignment
    }

    //getter for clusters
    public Cluster[] getClusters() {
        return clusters;
    }

    // getter for pointsToCentroidHash
    public Map<Integer, Integer> getPointsToCentroidHash() {
        return pointsToCentroidHash;
    }


    private double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
