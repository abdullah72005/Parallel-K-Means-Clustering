import dataSet.DataSetLoader;
import main.Point;
import main.KMeansConfig;
import main.KMeansSequential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
           List<Point> tmp =  DataSetLoader.loadCSV("D:\\code\\java\\untitled\\src\\dataSet\\small.csv");
           int k = DataSetLoader.getK(tmp);
           KMeansConfig config = new KMeansConfig(k,1000,1);

           ArrayList<Point> points = new ArrayList<Point>(tmp);
           KMeansSequential seq = new KMeansSequential(points,config);
           Point[] res =  seq.run();
           for(Point point : res){
               System.out.println(point.toString());
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}




