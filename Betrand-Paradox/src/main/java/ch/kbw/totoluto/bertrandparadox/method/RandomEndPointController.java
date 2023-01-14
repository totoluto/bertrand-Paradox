package ch.kbw.totoluto.bertrandparadox.method;

import ch.kbw.totoluto.bertrandparadox.method.model.Circle;
import ch.kbw.totoluto.bertrandparadox.method.model.Line;
import ch.kbw.totoluto.bertrandparadox.method.model.Triangle;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author totoluto (Dev alias)
 * @version 01.12.2022
 */

public class RandomEndPointController {
    public Circle circle;
    public Triangle triangle;
    public GraphicsContext graphicsContext;
    public Canvas canvas;

    public RandomEndPointController(Circle circle, Triangle triangle){
        this.circle = circle;
        this.triangle = triangle;
    }

    //generate lines for later usage
    public ArrayList<Line> generateLinesWithRandomStartPoint(int iterations){
        ArrayList<Point2D> pointsOnCircle = new ArrayList();
        ArrayList<Line> lines = new ArrayList();
        for (int i = 0; i < iterations; i++) {
            double angle = Math.toRadians(((double) i / iterations) * 360d);
            pointsOnCircle.add(new Point2D(Math.cos(angle) * circle.getRadius() + circle.getMid().getX() * 2, Math.sin(angle) * circle.getRadius() + circle.getMid().getY() + circle.getRadius()));
        }
        for (int i = 0; i < iterations; i++) {
            Line line = new Line();
            Random random = new Random();
            line.setStartCord(pointsOnCircle.get(random.nextInt(pointsOnCircle.size())));
            line.setEndCord(pointsOnCircle.get(random.nextInt(pointsOnCircle.size())));
            lines.add(line);
        }
        return lines;
    }
}
