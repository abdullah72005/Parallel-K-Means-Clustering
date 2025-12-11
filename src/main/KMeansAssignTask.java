package main;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class KMeansAssignTask extends RecursiveTask<Cluster[]> {
    private ArrayList<Point> points;
    private Cluster[] clusters;
    private int start;
    private int end;
    private KMeansConfig config;


    public KMeansAssignTask(ArrayList<Point> points, Cluster[] clusters, int start, int end, KMeansConfig config) {
        this.points = points;
        this.clusters = clusters;
        this.start = start;
        this.end = end;
        this.config = config;
    }


    @Override
    protected Cluster[] compute() {
        // if base condition run sequentially
        if (end - start <= config.threshold) {
            Cluster[] localClusters = createLocalClusters();
            assignPointsLocal(localClusters);
            return localClusters;
        }

        // split task
        int mid = (start + end) / 2;

        KMeansAssignTask left = new KMeansAssignTask(points, clusters, start, mid, config);
        KMeansAssignTask right = new KMeansAssignTask(points, clusters, mid, end, config);

        left.fork();

        Cluster[] rightRes = right.compute();
        Cluster[] leftRes = left.join();

        return merge(leftRes, rightRes);
    }

    private Cluster[] createLocalClusters() {
        Cluster[] local = new Cluster[clusters.length];
        for (int i = 0; i < clusters.length; i++) {
            local[i] = new Cluster(clusters[i].getCentroid());
        }
        return local;
    }

    private void assignPointsLocal(Cluster[] localClusters) {
        for (int i = start; i < end; i++){
            Point point = this.points.get(i);
            double closest_distance = Double.POSITIVE_INFINITY;
            Cluster closest_ref = null;
            for (Cluster cluster : localClusters) {
                double distance = distance(point , cluster.getCentroid());
                if (distance < closest_distance) {
                    closest_distance = distance;
                    closest_ref = cluster;
                }
            }
            if (closest_ref != null) {
                closest_ref.addPoint(point);
            }
        }
    }

    private Cluster[] merge(Cluster[] left, Cluster[] right) {
        for (int i = 0; i < left.length; i++) {
            left[i].addPoints(right[i].getPoints());
        }
        return left;
    }


    private double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

}
