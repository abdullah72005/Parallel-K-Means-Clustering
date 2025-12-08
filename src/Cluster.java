public class Cluster {
    int centroidX;
    int centroidY;
    point[] points;

    public int getCentroidX() {
        return centroidX;
    }

    public int getCentroidY() {
        return centroidY;
    }

    public point[] getPoints() {
        return points;
    }

    public Cluster(point[] points,int centroidX,int centroidY) {
        this.centroidX = centroidX;
        this.centroidY = centroidY;
        this.points = points;
    }
}
