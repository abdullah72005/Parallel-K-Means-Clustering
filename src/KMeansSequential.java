import java.util.ArrayList;

public class KMeansSequential {
    private ArrayList<Point> Points;
    private KMeansConfig KMeansConfig;
    private ArrayList<Point> centroids;
    private Cluster clusters;
    public KMeansSequential(ArrayList<Point> points,KMeansConfig KMeansConfig) {
        this.Points = points;
        this.KMeansConfig = KMeansConfig;
    }

    private void initialize_centroids() {
        for (int i = 0; i < this.KMeansConfig.k; i++) {
            double x = Math.random() * 100;
            double y = Math.random() * 100;
            Point centroid = new Point(x, y);
            this.centroids.add(centroid);
        }
    }

    private void assign_points() {
        for (Point point : this.Points){
            int closest;
            for (Point centroid : this.centroids) {

            }
        }
    }
}
