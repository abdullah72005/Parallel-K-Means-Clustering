package main;

import java.util.ArrayList;

public class KMeansExperiment  {
    private ArrayList<Point> points;
    private KMeansConfig config;
    private KMeansParallel parallel;
    private KMeansSequential sequential;
    private long parallel_time;
    private long sequential_time;
    public KMeansExperiment(ArrayList<Point> points,KMeansConfig config){
        this.points = points;
        this.config = config;
    }


    public void runParallel(){
        double best_sse = Double.POSITIVE_INFINITY;
        long start = System.nanoTime();
        for (int i = 0; i < config.i; i++) {
            KMeansParallel cur_parallel = new KMeansParallel(points,config);
            cur_parallel.run();
            if (best_sse > cur_parallel.getSSE()){
                 best_sse = cur_parallel.getSSE();
                 parallel = cur_parallel;
            }
        }
        long end = System.nanoTime();
        parallel_time = end - start;
    }

    public void runSequential(){
        double best_sse = Double.POSITIVE_INFINITY;
        long start = System.nanoTime();
        for (int i = 0; i < config.i; i++) {
            KMeansSequential cur_sequential = new KMeansSequential(points,config);
            cur_sequential.run();
            if (best_sse > cur_sequential.getSSE()){
                best_sse = cur_sequential.getSSE();
                sequential = cur_sequential;
            }
        }
        long end = System.nanoTime();
        sequential_time = end - start;
    }

    public void runBoth(){
        runSequential();
        runParallel();
    }

    public long getParallel_time() {
        return parallel_time;
    }

    public long getSequential_time() {
        return sequential_time;
    }

    public KMeansParallel getParallel() {
        return parallel;
    }

    public KMeansSequential getSequential() {
        return sequential;
    }
}
