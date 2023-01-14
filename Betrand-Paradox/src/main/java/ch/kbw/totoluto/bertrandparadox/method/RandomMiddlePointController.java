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
 * @version 14.01.2023
 */

public class RandomMiddlePointController {
    public Circle circle;
    public Triangle triangle;
    public GraphicsContext graphicsContext;
    public Canvas canvas;

    public RandomMiddlePointController(){
        this.circle = new Circle(150);
        this.triangle = new Triangle();
    }

    //Set canvas of fxml to this one
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
    }

    //Clear and draw triangle and circle
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


    //Generate Random Points
    public ArrayList<Line> generateLinesWithRandomMiddlePoint(int iterations){
        ArrayList<Line> lines = new ArrayList<>();
        double radius = circle.getRadius();
        Point2D mid = new Point2D(circle.getMid().getX() * 2, circle.getMid().getY() * 51);
        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            //Create random point
            double angle = random.nextDouble() * 2 * Math.PI;
            double hyp = Math.sqrt(Math.random()) * radius;
            double adjacent  = Math.cos(angle) * hyp;
            double opposite = Math.sin(angle) * hyp;
            Point2D point = new Point2D(mid.getX() + adjacent, mid.getY() + opposite);

            //Calculate angle
            double angle2 = Math.atan2(point.getX() - mid.getX(), point.getY() - mid.getY()) * 180 / Math.PI;
            angle2 = angle2 + 90;
            //Angle used to get second point on opposite site
            double angle3 = angle2 + 180;

            //Converting angles from radians to degrees
            angle2 = angle2 * Math.PI / 180;
            angle3 = angle3 * Math.PI / 180;

            //Calculating intersection
            double adjacentOfLine = Math.sqrt(Math.pow((mid.getX() - point.getX()), 2) + Math.pow((mid.getY() - point.getY()), 2));
            double intersection = Math.sqrt(Math.pow(radius, 2) - Math.pow(adjacentOfLine, 2));

            //Calculating points
            double startX = point.getX() + intersection * Math.sin(angle3);
            double startY = point.getY() + intersection * Math.cos(angle3);

            double endX = point.getX() + intersection * Math.sin(angle2);
            double endY = point.getY() + intersection * Math.cos(angle2);

            //Creating a line using calculated points
            Line line = new Line();
            line.setStartCord(new Point2D(startX, startY));
            line.setEndCord(new Point2D(endX, endY));
            lines.add(line);
        }

        return lines;
    }

    public void drawLines(Button start, Button reset, TextField iterationInput, TextField delayInput, Label probabilityLabel, Button endpoint, Button random, Button middle){
        //AtomicReference because during thread var can't be changed normally (it's just a counter)
        AtomicReference<Integer> longLines = new AtomicReference<>(0);
        AtomicReference<Integer> shortLines = new AtomicReference<>(0);
        //Save all generated lines in an arraylist
        ArrayList<Line> lines = generateLinesWithRandomMiddlePoint(Integer.parseInt(iterationInput.getText()));

        //Disable buttons and Inputs to prevent disturbing actions
        toggleInputs(true, start, reset, iterationInput, delayInput, endpoint, random, middle);
        //Create a thread to visualize the lines and create a delay between the actions
        new Thread(() -> {
            for (Line line: lines) {
                try{
                    Thread.sleep(Integer.parseInt(delayInput.getText()));
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    graphicsContext.setLineWidth(1);
                    //Check if length is greater than triangle length and change color to visualize line
                    if(triangle.getLength() <= line.getLength()){
                        graphicsContext.setStroke(Color.rgb(255, 0, 0));
                        longLines.updateAndGet(v -> v + 1);
                    }else{
                        graphicsContext.setStroke(Color.rgb(119, 221, 231));
                        shortLines.updateAndGet(v -> v + 1);
                    }
                    //Draw the line
                    graphicsContext.strokeLine(line.getStartCord().getX(), line.getStartCord().getY(), line.getEndCord().getX(), line.getEndCord().getY());
                    //Calculate probability
                    double longLinesCount = longLines.get().doubleValue();
                    double shortLinesCount = shortLines.get().doubleValue();
                    double total = shortLinesCount + longLinesCount;
                    double probability = 100 * (longLinesCount / total);
                    probabilityLabel.setText(probability + "%");
                });
            }

            //Draw over lines to visualize the lines in the circle and triangle
            createCircle();
            createTriangle();
            //Toggle back inputs and buttons
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
