public class KMeansConfig {
    int k;
    int maxIterations;
    double tolerance;

    public KMeansConfig(int k, int maxIterations, int tolerance) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
    }

    public int getK() {
        return k;
    }

    public double getTolerance() {
        return tolerance;
    }

    public int getMaxIterations() {
        return maxIterations;
    }
}
