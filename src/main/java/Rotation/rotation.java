package org.example.javafx._3DTransformations;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class RotateTriangle extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int TRIANGLE_SIZE = 200;
    private double rotationAngle = 30;

    public RotateTriangle() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Center the triangle
        g2d.translate(WIDTH / 2, HEIGHT / 2);

        // Create a BufferedImage to draw the triangle
        BufferedImage triangleImage = new BufferedImage(TRIANGLE_SIZE, TRIANGLE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D triangleGraphics = triangleImage.createGraphics();

        // Draw the triangle
        drawTriangle(triangleGraphics, TRIANGLE_SIZE);

        // Create an AffineTransform for rotation
        AffineTransform rotateTransform = AffineTransform.getRotateInstance(rotationAngle, (double) TRIANGLE_SIZE / 2, TRIANGLE_SIZE / 2);

        // Apply the rotation transformation
        AffineTransformOp rotateOp = new AffineTransformOp(rotateTransform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedTriangleImage = rotateOp.filter(triangleImage, null);

        // Draw the rotated triangle
        g2d.drawImage(rotatedTriangleImage, -TRIANGLE_SIZE / 2, -TRIANGLE_SIZE / 2, null);

        // Update the rotation angle for the next frame
        rotationAngle += 0.01;
    }

    private void drawTriangle(Graphics2D g2d, int size) {
        g2d.setColor(Color.CYAN);
        Polygon triangle = new Polygon();
        triangle.addPoint(0, size);
        triangle.addPoint(size / 2, 0);
        triangle.addPoint(size, size);
        g2d.fillPolygon(triangle);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotate Triangle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new RotateTriangle());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Animate the triangle rotation
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    frame.repaint();
                }
            }).start();
        });
    }
}