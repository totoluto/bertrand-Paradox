package ch.kbw.totoluto.bertrandparadox;

import ch.kbw.totoluto.bertrandparadox.method.RandomEndPointController;
import ch.kbw.totoluto.bertrandparadox.method.RandomMiddlePointController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author totoluto (Dev alias)
 * @version 14.01.2023
 */

public class MethodController{
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

    public GraphicsContext graphicsContext;

    private String methodSwitch = "Radius";
    //Create Method controller
    private RandomEndPointController repc = new RandomEndPointController();
    private RandomMiddlePointController rmpc = new RandomMiddlePointController();

    @FXML
    public void start(ActionEvent e) throws IOException{
        //Check which method is selected and run the method
        switch (methodSwitch){
            case "Radius":
                probability.setText("NaN");
                //Do sth
                break;
            case "EndPoint":
                probability.setText("NaN");
                repc.setCanvas(canvas);
                repc.clearCanvas();
                repc.drawComponents();
                repc.drawLines(start, reset, iterations, delay, probability, endpoint, random, middle);
                break;
            case "MiddlePoint":
                probability.setText("NaN");
                rmpc.setCanvas(canvas);
                rmpc.clearCanvas();
                rmpc.drawComponents();
                rmpc.drawLines(start, reset, iterations, delay, probability, endpoint, random, middle);
                break;
        }
    }

    @FXML
    public void reset(ActionEvent e) throws IOException{
        //Clear canvas, reset probability and draw components
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        switch (methodSwitch){
            case "Radius":
                //Do sth
                break;
            case "EndPoint":
                repc.setCanvas(canvas);
                repc.drawComponents();
                break;
            case "MiddlePoint":
                rmpc.setCanvas(canvas);
                rmpc.drawComponents();
                break;
        }
    }


    //Change method and reset settings
    @FXML
    public void handleEndPoint(ActionEvent e) throws IOException{ //1/3
        methodSwitch = "EndPoint";
        method.setText("Random end point");
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        iterations.setText("1000");
        delay.setText("10");
        repc.setCanvas(canvas);
        repc.drawComponents();
    }

    @FXML
    public void handleMiddlePoint(ActionEvent e) throws IOException{ //1/4
        methodSwitch = "MiddlePoint";
        method.setText("Random middle point");
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        iterations.setText("1000");
        delay.setText("10");
        rmpc.setCanvas(canvas);
        rmpc.drawComponents();
    }

    @FXML
    public void handlePoint(ActionEvent e) throws IOException{ //1/2
        methodSwitch = "Radius";
        method.setText("Random radius");
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        iterations.setText("1000");
        delay.setText("10");
    }

    //Basic Methods

}
