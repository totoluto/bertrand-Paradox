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

    public void drawLines(Button start, Button reset, TextField iterationInput, TextField delayInput, Label probabilityLabel, Button endpoint, Button random, Button middle){
        AtomicReference<Integer> longLines = new AtomicReference<>(0);
        AtomicReference<Integer> shortLines = new AtomicReference<>(0);
        ArrayList<Line> lines = generateLinesWithRandomStartPoint(Integer.parseInt(iterationInput.getText()));


        toggleInputs(true, start, reset, iterationInput, delayInput, endpoint, random, middle);
        new Thread(() -> {
            for (Line line: lines) {
                try{
                    Thread.sleep(Integer.parseInt(delayInput.getText()));
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    graphicsContext.setLineWidth(1);
                    //Check if length is greater than Triangle length
                    if(triangle.getLength() <= line.getLength()){
                        graphicsContext.setStroke(Color.rgb(255, 0, 0));
                        longLines.updateAndGet(v -> v + 1);
                    }else{
                        graphicsContext.setStroke(Color.rgb(119, 221, 231));
                        shortLines.updateAndGet(v -> v + 1);
                    }
                    graphicsContext.strokeLine(line.getStartCord().getX(), line.getStartCord().getY(), line.getEndCord().getX(), line.getEndCord().getY());
                    double longLinesCount = longLines.get().doubleValue();
                    double shortLinesCount = shortLines.get().doubleValue();
                    double total = shortLinesCount + longLinesCount;
                    double probability = 100 * (longLinesCount / total);
                    probabilityLabel.setText(probability + "%");
                });
            }

            createCircle();
            createTriangle();
            toggleInputs(false, start, reset, iterationInput, delayInput, endpoint, random, middle);
        }).start();
    }

    public void toggleInputs(boolean state, Button start, Button reset, TextField iterationInput, TextField delayInput, Button endpoint, Button random, Button middle) {
        start.setDisable(state);
        reset.setDisable(state);
        iterationInput.setDisable(state);
        delayInput.setDisable(state);
        endpoint.setDisable(state);
        middle.setDisable(state);
        random.setDisable(state);
    }

}
