import java.util.ArrayList;

public class Cluster {
    ArrayList<Point> Points;
    Point centroid;

    public ArrayList<Point> getPoints() {
        return Points;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public void setPoints(ArrayList<Point> points) {
        this.Points = points;
    }

    public void addPoint(Point point) {
        this.Points.add(point);
    }

    public Cluster( Point centroid) {
        this.centroid = centroid;
    }
}
