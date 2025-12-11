package main;

import java.util.ArrayList;

public class KMeansSequential {
    private ArrayList<Point> Points;
    private KMeansConfig config;
    private Cluster[] clusters;
    private int noOfIterations = 0;

    public KMeansSequential(ArrayList<Point> points, KMeansConfig config) {
        this.Points = points;
        this.config = config;
        this.clusters = new Cluster[config.k];
        initialize_centroids();
    }

    protected void initialize_centroids() {
        for (int i = 0; i < this.config.k; i++) {
            double x = config.rng.nextDouble() * 100;
            double y = config.rng.nextDouble() * 100;
            Point centroid = new Point(x, y);
            this.clusters[i] = new Cluster(centroid);
        }
    }

    public void assign_points() {
        for ( Cluster c : clusters){
            c.setPoints(new ArrayList<>());
        }
        for (Point point : this.Points){
            double closest_distance = Double.POSITIVE_INFINITY;
            Cluster closest_ref = null;
            for (Cluster cluster : this.clusters) {
                double distance = config.distance(point , cluster.getCentroid());
                if (distance < closest_distance) {
                    closest_distance = distance;
                    closest_ref = cluster;
                }
            }
            try {
                closest_ref.addPoint(point);
            }
            catch (NullPointerException e){
                System.out.println("null pointer exception: " + e);
            }
        }
    }

    private boolean update_centroids(){
        Point old_point;
        Point new_point;
        double total_distance = 0;
        for (Cluster cluster : clusters){
            old_point = cluster.getCentroid();
            if ( cluster.getPoints().isEmpty() ) {
                continue;
            }
            double sumX = 0;
            double sumY = 0;
            for (Point point : cluster.getPoints()){
                sumX += point.getX();
                sumY += point.getY();
            }
            double meanX = sumX / cluster.getPoints().size();
            double meanY = sumY / cluster.getPoints().size();
            cluster.setCentroid(new Point(meanX,meanY));
            new_point = cluster.getCentroid();
            total_distance += config.distance(old_point,new_point);
        }
        if (total_distance < config.tolerance){
            return true;
        }
        return false;
    }

    public void run(){
        initialize_centroids();
        for (int i = 0; i < config.maxIterations; i++) {
            noOfIterations++;
            assign_points();
            boolean check = update_centroids();
            System.out.println(i);
            if (check){
                break;
            }
        }
        assign_points();
    }

    public Cluster[] getClusters(){
        return this.clusters;
    }

    public void setClusters(Cluster[] clusters) {
        this.clusters = clusters;
    }

    public double getSSE() {
        double sse = 0;
        for (Cluster c : clusters) {
            for (Point p : c.Points) {
                sse += config.distance(c.getCentroid(), p);
            }
        }

        return Math.floor(sse * 100) / 100;
    }

    public int getNoOfIterations() {
        return noOfIterations;
    }
}
