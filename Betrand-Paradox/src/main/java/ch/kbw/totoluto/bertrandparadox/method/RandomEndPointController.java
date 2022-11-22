package ch.kbw.totoluto.bertrandparadox.method;

import ch.kbw.totoluto.bertrandparadox.method.model.Circle;
import ch.kbw.totoluto.bertrandparadox.method.model.Triangle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author totoluto (Dev alias)
 * @version 15.11.2022
 */

public class RandomEndPointController {
    public Circle circle;
    public Triangle triangle;
    public GraphicsContext graphicsContext;
    public Canvas canvas;

    public RandomEndPointController(){
        this.circle = new Circle(150);
        this.triangle = new Triangle();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
    }

    public void drawComponents(){
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        createCircle();
        createTriangle();
    }

    public void createTriangle(){
        triangle.constructInCircle(circle);
        graphicsContext.setLineWidth(1.5);
        graphicsContext.setStroke(Color.rgb(54, 57, 59));
        graphicsContext.strokePolygon(triangle.getXPos(), triangle.getYPos(), 3);

    }

    public void createCircle(){
        circle.setPos(new Point2D(canvas.getWidth(), canvas.getHeight()));
        graphicsContext.setLineWidth(1.5);
        graphicsContext.setStroke(Color.rgb(54, 57, 59));
        graphicsContext.strokeOval(circle.getMid().getX(), circle.getMid().getY(), circle.getDiameter(), circle.getDiameter());
    }

    public void clearCanvas(){
        graphicsContext.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }
}
