package main;

import java.util.Random;

public class KMeansConfig {
    public final int k;
    public final int maxIterations;
    public final double tolerance;
    public int threshold;
    public final Random rng;

    public KMeansConfig(int k, int maxIterations, double tolerance, long seed) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
        this.rng = new Random(seed);
    }

    public KMeansConfig(int k, int maxIterations, double tolerance, long seed, int threshold) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
        this.rng = new Random(seed);
        this.threshold = threshold;
    }

    public double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}