
public class Cluster {
    int centroidX;
    int centroidY;
    Point[] Points;

    public int getCentroidX() {
        return centroidX;
    }

    public int getCentroidY() {
        return centroidY;
    }

    public Point[] getPoints() {
        return Points;
    }

    public Cluster(Point[] Points,int centroidX,int centroidY) {
        this.centroidX = centroidX;
        this.centroidY = centroidY;
        this.Points = Points;
    }
}
