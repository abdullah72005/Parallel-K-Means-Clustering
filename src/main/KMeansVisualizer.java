package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Map;

public class KMeansVisualizer extends JFrame {
    private KMeansVisualizerPanel panel;

    public KMeansVisualizer(Map<Integer, Point> pointsHash, Map<Integer, Integer> assignments, Cluster[] clusters) {
        setTitle("K-Means Clustering Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        panel = new KMeansVisualizerPanel(pointsHash, assignments, clusters);
        add(panel);

        setVisible(true);
    }

    static class KMeansVisualizerPanel extends JPanel {
        private Map<Integer, Point> pointsHash;
        private Map<Integer, Integer> assignments;
        private Cluster[] clusters;
        private static final double PADDING = 50;
        private static final double POINT_RADIUS = 4;
        private static final double CENTROID_RADIUS = 8;
        private double minX, maxX, minY, maxY;

        private Color[] clusterColors = {
                Color.RED, Color.BLUE, Color.GREEN, new Color(255, 165, 0), new Color(128, 0, 128),
                Color.CYAN, Color.MAGENTA, new Color(128, 128, 0), new Color(165, 42, 42), Color.PINK
        };

        public KMeansVisualizerPanel(Map<Integer, Point> pointsHash, Map<Integer, Integer> assignments, Cluster[] clusters) {
            this.pointsHash = pointsHash;
            this.assignments = assignments;
            this.clusters = clusters;
            calculateBounds();
        }

        private void calculateBounds() {
            minX = Double.MAX_VALUE;
            maxX = -Double.MAX_VALUE;
            minY = Double.MAX_VALUE;
            maxY = -Double.MAX_VALUE;

            // Check all points
            for (Point point : pointsHash.values()) {
                minX = Math.min(minX, point.getX());
                maxX = Math.max(maxX, point.getX());
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }

            // Check all centroids
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
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw grid
            drawGrid(g2d);

            // Draw points and connections
            for (Map.Entry<Integer, Point> entry : pointsHash.entrySet()) {
                Integer pointId = entry.getKey();
                Point point = entry.getValue();
                
                // Get the cluster ID this point is assigned to
                Integer clusterId = assignments.get(pointId);
                if (clusterId == null || clusterId < 0 || clusterId >= clusters.length) continue;
                
                // Get the centroid from the cluster
                Point centroid = clusters[clusterId].getCentroid();
                
                Color pointColor = clusterColors[clusterId % clusterColors.length];

                // Draw line from point to centroid
                double pointScreenX = mapX(point.getX());
                double pointScreenY = mapY(point.getY());
                double centroidScreenX = mapX(centroid.getX());
                double centroidScreenY = mapY(centroid.getY());

                g2d.setColor(pointColor);
                g2d.setStroke(new BasicStroke(0.5f));
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.drawLine((int) pointScreenX, (int) pointScreenY,
                            (int) centroidScreenX, (int) centroidScreenY);

                // Draw point
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2d.setColor(pointColor);
                Ellipse2D.Double circle = new Ellipse2D.Double(
                        pointScreenX - POINT_RADIUS, pointScreenY - POINT_RADIUS,
                        POINT_RADIUS * 2, POINT_RADIUS * 2);
                g2d.fill(circle);

                // Draw centroid (larger)
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2.0f));
                Ellipse2D.Double centroidCircle = new Ellipse2D.Double(
                        centroidScreenX - CENTROID_RADIUS, centroidScreenY - CENTROID_RADIUS,
                        CENTROID_RADIUS * 2, CENTROID_RADIUS * 2);
                g2d.fill(centroidCircle);
                g2d.setColor(pointColor);
                g2d.draw(centroidCircle);
            }

            // Draw legend
            drawLegend(g2d);
        }

        private void drawGrid(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(0.5f));

            int gridLines = 10;
            for (int i = 0; i <= gridLines; i++) {
                double x = PADDING + (getWidth() - 2 * PADDING) * i / gridLines;
                g2d.drawLine((int) x, (int) PADDING, (int) x, (int) (getHeight() - PADDING));
            }

            for (int i = 0; i <= gridLines; i++) {
                double y = PADDING + (getHeight() - 2 * PADDING) * i / gridLines;
                g2d.drawLine((int) PADDING, (int) y, (int) (getWidth() - PADDING), (int) y);
            }

            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawLine((int) PADDING, (int) (getHeight() - PADDING),
                        (int) (getWidth() - PADDING), (int) (getHeight() - PADDING));
            g2d.drawLine((int) PADDING, (int) PADDING, (int) PADDING, (int) (getHeight() - PADDING));
        }

        private void drawLegend(Graphics2D g2d) {
            double legendX = getWidth() - 150;
            double legendY = PADDING;
            double legendItemHeight = 20;
            int legendHeight = Math.min(clusters.length * (int)legendItemHeight + 20, 300);

            g2d.setColor(Color.WHITE);
            g2d.fillRect((int) (legendX - 10), (int) (legendY - 10), 160, legendHeight);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawRect((int) (legendX - 10), (int) (legendY - 10), 160, legendHeight);

            g2d.setFont(new Font("Arial", Font.PLAIN, 12));

            for (int i = 0; i < clusters.length && i < 15; i++) {
                Color color = clusterColors[i % clusterColors.length];
                double y = legendY + i * legendItemHeight;

                // Color box
                g2d.setColor(color);
                g2d.fillRect((int) legendX, (int) y, 12, 12);

                // Label
                g2d.setColor(Color.BLACK);
                g2d.drawString("Cluster " + i + " (" + clusters[i].getPoints().size() + ")", 
                             (int) (legendX + 18), (int) (y + 10));
            }
        }

        private double mapX(double x) {
            double range = maxX - minX;
            if (range == 0) range = 1;
            return PADDING + (x - minX) / range * (getWidth() - 2 * PADDING);
        }

        private double mapY(double y) {
            double range = maxY - minY;
            if (range == 0) range = 1;
            return getHeight() - PADDING - (y - minY) / range * (getHeight() - 2 * PADDING);
        }
    }
}
