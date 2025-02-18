package ch.kbw.totoluto.bertrandparadox.method;

import ch.kbw.totoluto.bertrandparadox.method.model.Circle;
import ch.kbw.totoluto.bertrandparadox.method.model.Line;
import ch.kbw.totoluto.bertrandparadox.method.model.Triangle;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author totoluto (Dev alias)
 * @version 14.01.2023
 */

public class RandomRadiusPointController {
    public Circle circle;
    public Triangle triangle;

    public RandomRadiusPointController(Circle circle, Triangle triangle) {
        this.circle = circle;
        this.triangle = triangle;
    }

    // Generate Random Points
    public ArrayList<Line> generateLinesWithRandomRadiusPoint(int iterations) {
        ArrayList<Line> lines = new ArrayList<>();
        double radius = circle.getRadius();
        Point2D mid = new Point2D(circle.getMid().getX() * 2, circle.getMid().getY() * 51);
        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            // Create random point between random circumference and circle Center
            double randomAngle = random.nextDouble() * Math.PI * 2.0;
            Point2D circumferencePoint = new Point2D(Math.cos(randomAngle) * radius + mid.getX(), Math.sin(randomAngle) * radius + mid.getY());
            double randomFactor = random.nextDouble();

            Point2D point = new Point2D(
                    mid.getX() + randomFactor * (circumferencePoint.getX() - mid.getX()),
                    mid.getY() + randomFactor * (circumferencePoint.getY() - mid.getY())
            );

            // Calculate angle
            double angle = 90 + Math.atan2(point.getX() - mid.getX(), point.getY() - mid.getY()) * 180 / Math.PI;

            // Angle used to get second point on opposite site
            double angle2 = angle + 180;

            // Converting angles from radians to degrees
            angle = angle * Math.PI / 180;
            angle2 = angle2 * Math.PI / 180;

            // Calculating intersection
            double adjacentOfLine = Math.sqrt(Math.pow((mid.getX() - point.getX()), 2) + Math.pow((mid.getY() - point.getY()), 2));
            double intersection = Math.sqrt(Math.pow(radius, 2) - Math.pow(adjacentOfLine, 2));

            // Calculating points
            double startX = point.getX() + intersection * Math.sin(angle2);
            double startY = point.getY() + intersection * Math.cos(angle2);

            double endX = point.getX() + intersection * Math.sin(angle);
            double endY = point.getY() + intersection * Math.cos(angle);

            // Creating a line using calculated points
            Line line = new Line();
            line.setStartCord(new Point2D(startX, startY));
            line.setEndCord(new Point2D(endX, endY));
            lines.add(line);
        }

        return lines;
    }
}
