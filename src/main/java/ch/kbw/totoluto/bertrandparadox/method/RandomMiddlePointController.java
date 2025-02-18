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

public class RandomMiddlePointController {
    public Circle circle;
    public Triangle triangle;

    public RandomMiddlePointController(Circle circle, Triangle triangle) {
        this.circle = circle;
        this.triangle = triangle;
    }

    // Generate Random Points
    public ArrayList<Line> generateLinesWithRandomMiddlePoint(int iterations) {
        ArrayList<Line> lines = new ArrayList<>();
        double radius = circle.getRadius();
        Point2D mid = new Point2D(circle.getMid().getX() * 2, circle.getMid().getY() * 51);
        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            // Create random point
            double angle = random.nextDouble() * 2 * Math.PI;
            double hyp = Math.sqrt(Math.random()) * radius;
            double adjacent  = Math.cos(angle) * hyp;
            double opposite = Math.sin(angle) * hyp;
            Point2D point = new Point2D(mid.getX() + adjacent, mid.getY() + opposite);

            // Calculate angle
            double angle2 = 90 + Math.atan2(point.getX() - mid.getX(), point.getY() - mid.getY()) * 180 / Math.PI;

            // Angle used to get second point on opposite site
            double angle3 = angle2 + 180;

            // Converting angles from radians to degrees
            angle2 = angle2 * Math.PI / 180;
            angle3 = angle3 * Math.PI / 180;

            // Calculating intersection
            double adjacentOfLine = Math.sqrt(Math.pow((mid.getX() - point.getX()), 2) + Math.pow((mid.getY() - point.getY()), 2));
            double intersection = Math.sqrt(Math.pow(radius, 2) - Math.pow(adjacentOfLine, 2));

            // Calculating points
            double startX = point.getX() + intersection * Math.sin(angle3);
            double startY = point.getY() + intersection * Math.cos(angle3);

            double endX = point.getX() + intersection * Math.sin(angle2);
            double endY = point.getY() + intersection * Math.cos(angle2);

            // Creating a line using calculated points
            Line line = new Line();
            line.setStartCord(new Point2D(startX, startY));
            line.setEndCord(new Point2D(endX, endY));
            lines.add(line);
        }

        return lines;
    }
}
