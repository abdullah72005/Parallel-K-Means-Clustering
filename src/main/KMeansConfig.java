package main;

import java.util.Random;

public class KMeansConfig {
    public final int k;
    public final int maxIterations;
    public final double tolerance;
    public final Random rng;

    public KMeansConfig(int k, int maxIterations, double tolerance, long seed) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
        this.rng = new Random(seed);
    }
}
