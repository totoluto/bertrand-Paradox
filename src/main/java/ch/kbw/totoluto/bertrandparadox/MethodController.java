package ch.kbw.totoluto.bertrandparadox;

import ch.kbw.totoluto.bertrandparadox.method.RandomEndPointController;
import ch.kbw.totoluto.bertrandparadox.method.RandomMiddlePointController;
import ch.kbw.totoluto.bertrandparadox.method.RandomRadiusPointController;
import ch.kbw.totoluto.bertrandparadox.method.model.Circle;
import ch.kbw.totoluto.bertrandparadox.method.model.Line;
import ch.kbw.totoluto.bertrandparadox.method.model.Triangle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author totoluto (Dev alias)
 * @version 14.01.2023
 */

public class MethodController {
    @FXML
    private Label method;
    @FXML
    private Canvas canvas;
    @FXML
    private Label probability;
    @FXML
    private TextField delay;
    @FXML
    private TextField iterations;
    @FXML
    private Button start;
    @FXML
    private Button reset;
    @FXML
    private Button endpoint;
    @FXML
    private Button middle;
    @FXML
    private Button random;

    public Circle circle = new Circle(150);
    public Triangle triangle = new Triangle();

    private String methodSwitch = "Radius";

    // Create Method controller
    // 1/2
    private final RandomEndPointController repc = new RandomEndPointController(circle, triangle);
    // 1/4
    private final RandomMiddlePointController rmpc = new RandomMiddlePointController(circle, triangle);
    // 1/3
    private final RandomRadiusPointController rrpc = new RandomRadiusPointController(circle, triangle);

    @FXML
    public void start(ActionEvent e) throws IOException {
        // Clear canvas, reset probability, draw components and draw lines
        clearCanvas();
        probability.setText("NaN");
        drawComponents();
        drawLines();
    }

    @FXML
    public void initialize(){
        drawComponents();
    }

    @FXML
    public void reset(ActionEvent e) throws IOException {
        // Clear canvas, reset probability and draw components
        clearCanvas();
        probability.setText("NaN");
        drawComponents();
    }


    // Change method and reset settings
    @FXML
    public void handleEndPoint(ActionEvent e) throws IOException {
        // 1/3 probability
        methodSwitch = "EndPoint";
        method.setText("Random end point");
        clearCanvas();
        probability.setText("NaN");
        drawComponents();
    }

    @FXML
    public void handleMiddlePoint(ActionEvent e) throws IOException {
        // 1/4 probability
        methodSwitch = "MiddlePoint";
        method.setText("Random middle point");
        clearCanvas();
        probability.setText("NaN");
        drawComponents();
    }

    @FXML
    public void handlePoint(ActionEvent e) throws IOException {
        // 1/2 probability
        methodSwitch = "Radius";
        method.setText("Random radius");
        clearCanvas();
        probability.setText("NaN");
        drawComponents();
    }

    // Clear and draw triangle and circle
    public void drawComponents() {
        clearCanvas();
        createCircle();
        createTriangle();
    }

    public void createTriangle() {
        triangle.constructInCircle(circle);
        canvas.getGraphicsContext2D().setLineWidth(1.5);
        canvas.getGraphicsContext2D().setStroke(Color.rgb(54, 57, 59));
        canvas.getGraphicsContext2D().strokePolygon(triangle.getXPos(), triangle.getYPos(), 3);
    }

    public void createCircle() {
        circle.setPos(new Point2D(canvas.getWidth(), canvas.getHeight()));
        canvas.getGraphicsContext2D().setLineWidth(1.5);
        canvas.getGraphicsContext2D().setStroke(Color.rgb(54, 57, 59));
        canvas.getGraphicsContext2D().strokeOval(circle.getMid().getX(), circle.getMid().getY(), circle.getDiameter(), circle.getDiameter());
    }

    public void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawLines(){
        // AtomicReference because during thread var can't be changed normally (it's just a counter)
        AtomicReference<Integer> longLines = new AtomicReference<>(0);
        AtomicReference<Integer> shortLines = new AtomicReference<>(0);

        // Choose Method and save all generated lines in an arraylist
        ArrayList<Line> lines;
        switch (methodSwitch){
            case "Radius":
                lines = rrpc.generateLinesWithRandomRadiusPoint(Integer.parseInt(iterations.getText()));
                break;
            case "EndPoint":
                lines = repc.generateLinesWithRandomStartPoint(Integer.parseInt(iterations.getText()));
                break;
            case "MiddlePoint":
                lines = rmpc.generateLinesWithRandomMiddlePoint(Integer.parseInt(iterations.getText()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + methodSwitch);
        }

        // Disable buttons and Inputs to prevent disturbing actions
        toggleInputs(true);

        // Create a thread to visualize the lines and create a delay between the actions
        ArrayList<Line> finalLines = lines;
        new Thread(() -> {
            for (Line line: finalLines) {
                try {
                    Thread.sleep(Integer.parseInt(delay.getText()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    canvas.getGraphicsContext2D().setLineWidth(1);

                    // Check if length is greater than triangle length and change color to visualize line
                    if (triangle.getLength() <= line.getLength()) {
                        canvas.getGraphicsContext2D().setStroke(Color.rgb(255, 0, 0));
                        longLines.updateAndGet(v -> v + 1);
                    } else{
                        canvas.getGraphicsContext2D().setStroke(Color.rgb(119, 221, 231));
                        shortLines.updateAndGet(v -> v + 1);
                    }

                    // Draw the line
                    canvas.getGraphicsContext2D().strokeLine(line.getStartCord().getX(), line.getStartCord().getY(), line.getEndCord().getX(), line.getEndCord().getY());

                    // Calculate probability
                    double longLinesCount = longLines.get().doubleValue();
                    double shortLinesCount = shortLines.get().doubleValue();
                    double total = shortLinesCount + longLinesCount;
                    double probabilityPercentage = 100 * (longLinesCount / total);
                    probability.setText(probabilityPercentage + "%");
                });
            }

            // Draw over lines to visualize the lines in the circle and triangle
            createCircle();
            createTriangle();

            // Toggle back inputs and buttons
            toggleInputs(false);
        }).start();
    }

    public void toggleInputs(boolean state) {
        start.setDisable(state);
        reset.setDisable(state);
        iterations.setDisable(state);
        delay.setDisable(state);
        endpoint.setDisable(state);
        middle.setDisable(state);
        random.setDisable(state);
    }
}
