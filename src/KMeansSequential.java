import java.util.ArrayList;

public class KMeansSequential {
    private ArrayList<Point> Points;
    private KMeansConfig config;
    private Point[] centroids;
    private Cluster[] clusters;
    public KMeansSequential(ArrayList<Point> points,KMeansConfig config) {
        this.Points = points;
        this.config = config;
        this.centroids  = new Point[config.k];
        this.clusters = new Cluster[config.k];
    }

    private void initialize_centroids() {
        for (int i = 0; i < this.config.k; i++) {
            double x = Math.random() * 100;
            double y = Math.random() * 100;
            Point centroid = new Point(x, y);
            this.centroids[i] = centroid;
            this.clusters[i] = new Cluster(centroid);
        }
    }

    private void assign_points() {
        for (Point point : this.Points){
            double closest_distance = Double.POSITIVE_INFINITY;
            Cluster closest_ref = null;
            for (Cluster cluster : this.clusters) {
                double distance = Math.sqrt(Math.pow((cluster.centroid.getX() - point.getX()), 2) + Math.pow((cluster.centroid.getY() - point.getY()), 2));
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



}
