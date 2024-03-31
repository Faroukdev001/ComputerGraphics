package DDAlgorithm;

public class DDALinee {
    public static void drawLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        double xinc = (double) dx / steps;
        double yinc = (double) dy / steps;

        double x = x1;
        double y = y1;

        for (int i = 0; i <= steps; i++) {
            // Plot the point (x, y)
            System.out.println("Point: (" + Math.round(x) + ", " + Math.round(y) + ")");
            x += xinc;
            y += yinc;
        }
    }

    public static void main(String[] args) {
        int x1 = 2, y1 = 2;
        int x2 = 8, y2 = 10;
        drawLine(x1, y1, x2, y2);
    }
}