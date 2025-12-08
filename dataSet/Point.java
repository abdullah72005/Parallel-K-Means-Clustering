public record Point(int id, double x, double y, int label) {
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
