package dataSet;

public class Point {
    private int id;
    private final double x;
    private final double y;
    private int label;

    public Point(int id, double x, double y, int label) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public Point(double x, double y) {
        this.id = -1;
        this.x = x;
        this.y = y;
        this.label = -1;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLabel() {
        return label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    /**
     * Parses a CSV line into a Point object.
     * Expected format: id,x,y,label
     */
    public static Point parsePoint(String line) {
        String[] parts = line.split(",");

        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    String.format("Expected 4 fields, found %d", parts.length));
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            double x = Double.parseDouble(parts[1].trim());
            double y = Double.parseDouble(parts[2].trim());
            int label = Integer.parseInt(parts[3].trim());

            return new Point(id, x, y, label);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid number format: %s", e.getMessage()), e);
        }
    }

    @Override
    public String toString() {
        return String.format("Point{id=%d, x=%.6f, y=%.6f, label=%d}", id, x, y, label);
    }
}
