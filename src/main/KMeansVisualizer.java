package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KMeansVisualizer extends JFrame {

    private ClusterPlotter plotter;

    public KMeansVisualizer(Cluster[] clusters, ArrayList<Point> allPoints,
                            double sse, double timeSeconds, int iterations,
                            boolean showStats) {

        setTitle("K-Means Clustering Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(900, 950); 
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); 
        setContentPane(content);

        plotter = new ClusterPlotter(clusters, allPoints);
        add(plotter, BorderLayout.CENTER);

        if (showStats) {
            JPanel stats = new JPanel(new GridLayout(1, 3));
            stats.add(new JLabel("SSE: " + sse, SwingConstants.CENTER));
            stats.add(new JLabel(String.format("Time (s): %.4f", timeSeconds), SwingConstants.CENTER));
            stats.add(new JLabel("Iterations: " + iterations, SwingConstants.CENTER));
            add(stats, BorderLayout.SOUTH);
        }

        setVisible(true);
    }

    
    private static class ClusterPlotter extends JPanel {
        private Cluster[] clusters;
        private ArrayList<Point> allPoints;
        private Color[] colors;
        private static final int PADDING = 50;
        private static final int POINT_RADIUS = 4;
        private static final int CENTROID_RADIUS = 6;

        public ClusterPlotter(Cluster[] clusters, ArrayList<Point> allPoints) {
            this.clusters = clusters;
            this.allPoints = allPoints;
            this.colors = generateColors(clusters.length);
        }

        private Color[] generateColors(int k) {
            Color[] colors = new Color[k];
            for (int i = 0; i < k; i++) {
                colors[i] = new Color(
                    (int) (Math.random() * 200) + 55,
                    (int) (Math.random() * 200) + 55,
                    (int) (Math.random() * 200) + 55
                );
            }
            return colors;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            double plotWidth = width - 2 * PADDING;
            double plotHeight = height - 2 * PADDING;

            double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;

            for (Point point : allPoints) {
                minX = Math.min(minX, point.getX());
                maxX = Math.max(maxX, point.getX());
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }

            for (Cluster cluster : clusters) {
                Point centroid = cluster.getCentroid();
                minX = Math.min(minX, centroid.getX());
                maxX = Math.max(maxX, centroid.getX());
                minY = Math.min(minY, centroid.getY());
                maxY = Math.max(maxY, centroid.getY());
            }

            double rangeX = maxX - minX;
            double rangeY = maxY - minY;
            if (rangeX == 0) rangeX = 1;
            if (rangeY == 0) rangeY = 1;
            minX -= rangeX * 0.05;
            maxX += rangeX * 0.05;
            minY -= rangeY * 0.05;
            maxY += rangeY * 0.05;

            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(0.5f));

            for (int clusterIdx = 0; clusterIdx < clusters.length; clusterIdx++) {
                Cluster cluster = clusters[clusterIdx];
                Point centroid = cluster.getCentroid();
                int centroidX = scaleX(centroid.getX(), minX, maxX, plotWidth, PADDING);
                int centroidY = scaleY(centroid.getY(), minY, maxY, plotHeight, PADDING);

                for (Point point : cluster.getPoints()) {
                    int pointX = scaleX(point.getX(), minX, maxX, plotWidth, PADDING);
                    int pointY = scaleY(point.getY(), minY, maxY, plotHeight, PADDING);
                    g2d.drawLine(pointX, pointY, centroidX, centroidY);
                }
            }

            for (int clusterIdx = 0; clusterIdx < clusters.length; clusterIdx++) {
                Cluster cluster = clusters[clusterIdx];
                g2d.setColor(colors[clusterIdx]);

                for (Point point : cluster.getPoints()) {
                    int x = scaleX(point.getX(), minX, maxX, plotWidth, PADDING);
                    int y = scaleY(point.getY(), minY, maxY, plotHeight, PADDING);
                    g2d.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
                }
            }

            g2d.setColor(Color.BLACK);
            for (Cluster cluster : clusters) {
                Point centroid = cluster.getCentroid();
                int centroidX = scaleX(centroid.getX(), minX, maxX, plotWidth, PADDING);
                int centroidY = scaleY(centroid.getY(), minY, maxY, plotHeight, PADDING);
                g2d.fillOval(centroidX - CENTROID_RADIUS, centroidY - CENTROID_RADIUS, CENTROID_RADIUS * 2, CENTROID_RADIUS * 2);
            }

            
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            int legendY = 20;
            for (int clusterIdx = 0; clusterIdx < clusters.length; clusterIdx++) {
                Cluster cluster = clusters[clusterIdx];
                int pointCount = cluster.getPoints().size();
                String label = String.format("C%d (%d pts)", clusterIdx, pointCount);
                g2d.setColor(colors[clusterIdx]);
                int rectX = width - 160;
                g2d.fillRect(rectX, legendY, 12, 12);
                g2d.setColor(Color.BLACK);
                g2d.drawString(label, rectX + 18, legendY + 11);
                legendY += 20;
            }

            
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawLine(PADDING, height - PADDING, width - PADDING, height - PADDING); // X-axis
            g2d.drawLine(PADDING, PADDING, PADDING, height - PADDING); // Y-axis
            g2d.drawString(String.format("%.1f", minX), PADDING - 20, height - PADDING + 20);
            g2d.drawString(String.format("%.1f", maxX), width - PADDING - 20, height - PADDING + 20);
            g2d.drawString(String.format("%.1f", minY), PADDING - 40, PADDING + 5);
            g2d.drawString(String.format("%.1f", maxY), PADDING - 40, height - PADDING + 5);
        }

        private int scaleX(double x, double minX, double maxX, double plotWidth, int padding) {
            return (int) (padding + (x - minX) / (maxX - minX) * plotWidth);
        }

        private int scaleY(double y, double minY, double maxY, double plotHeight, int padding) {
            return (int) (padding + (maxY - y) / (maxY - minY) * plotHeight);
        }
    }
}
