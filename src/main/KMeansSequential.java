package main;

import java.util.ArrayList;

public class KMeansSequential {
    private ArrayList<Point> Points;
    private KMeansConfig config;
    private Cluster[] clusters;

    public KMeansSequential(ArrayList<Point> points, KMeansConfig config) {
        this.Points = points;
        this.config = config;
        this.clusters = new Cluster[config.k ];
        initialize_centroids();
    }

    private void initialize_centroids() {
        for (int i = 0; i < this.config.k; i++) {
            double x = config.rng.nextDouble() * 100;
            double y = config.rng.nextDouble() * 100;
            Point centroid = new Point(x, y);
            this.clusters[i] = new Cluster(centroid);
        }
    }

    private void assign_points() {
        for ( Cluster c : clusters){
            c.setPoints(new ArrayList<>());
        }
        for (Point point : this.Points){
            double closest_distance = Double.POSITIVE_INFINITY;
            Cluster closest_ref = null;
            for (Cluster cluster : this.clusters) {
                double distance = distance(point , cluster.getCentroid());
                if (distance < closest_distance) {
                    closest_distance = distance;
                    closest_ref = cluster;
                }
            }
            try {
                closest_ref.addPoint(point);
            }
            catch (NullPointerException e){
                System.out.println("wtf bro") ;
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
            total_distance += distance(old_point,new_point);
        }
        if (total_distance < config.tolerance){
            return true;
        }
        return false;
    }


    private double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void run(){
        initialize_centroids();
        for (int i = 0; i < config.maxIterations; i++) {
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
}
