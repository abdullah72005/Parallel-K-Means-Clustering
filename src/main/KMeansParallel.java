package main;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class KMeansParallel {
    private KMeansConfig config;
    private ArrayList<Point> points;
    private Cluster[] clusters;

    public ForkJoinPool pool = new ForkJoinPool();


    public KMeansParallel(ArrayList<Point> points, KMeansConfig config) {
        this.points = points;
        this.config = config;
        this.clusters = new Cluster[config.k];
    }

    private void par_initialize_centroids() {
        for (int i = 0; i < this.config.k; i++) {
            double x = config.rng.nextDouble() * 100;
            double y = config.rng.nextDouble() * 100;
            Point centroid = new Point(x, y);
            this.clusters[i] = new Cluster(centroid);
        }
    }

    public void run() {
        par_initialize_centroids();

        for (int i = 0; i < config.maxIterations; i++) {

            // Clear all clusters before assignment
            for (Cluster cluster : clusters) {
                cluster.setPoints(new ArrayList<>());
            }

            // assign points using recursive task then assigning it to my sequential object to call other funtions 
            KMeansAssignTask task = new KMeansAssignTask(points, clusters, 0, points.size(), config);
            clusters = pool.invoke(task);

            boolean check = par_update_centroids();
            System.out.println("Iteration " + i);
            if (check){
                break;
            }
        }

        // Final assignment
        for (Cluster cluster : clusters) {
            cluster.setPoints(new ArrayList<>());
        }
        KMeansAssignTask task = new KMeansAssignTask(points, clusters, 0, points.size(), config);
        clusters = pool.invoke(task);
    }

    public Cluster[] getClusters(){
        return this.clusters;
    }

    private boolean par_update_centroids() {
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

}
