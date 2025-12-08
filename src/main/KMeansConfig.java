package main;

public class KMeansConfig {
    public final int k;
    public final int maxIterations;
    public final double tolerance;

    public KMeansConfig(int k, int maxIterations, double tolerance) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
    }
}
