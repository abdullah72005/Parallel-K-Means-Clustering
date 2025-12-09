package main;

import java.util.ArrayList;

public class Cluster {
    ArrayList<Integer> PointsIds = new ArrayList<>();
    Point centroid;
    private final Integer id;

    public Cluster(Integer id, Point centroid) {
        this.centroid = centroid;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Integer> getPoints() {
        return PointsIds;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.PointsIds = points;
    }

    public void addPoint(Integer pointid) {
        this.PointsIds.add(pointid);
    }

    public void removePoint(Integer pointid) {
        this.PointsIds.remove(pointid);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "centroid=" + centroid.toString();
    }
}
