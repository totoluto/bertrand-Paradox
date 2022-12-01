package ch.kbw.totoluto.bertrandparadox;

import ch.kbw.totoluto.bertrandparadox.method.RandomEndPointController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author totoluto (Dev alias)
 * @version 15.11.2022
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


    private String methodSwitch = "Point";
    private RandomEndPointController repc = new RandomEndPointController();

    @FXML
    public void start(ActionEvent e) throws IOException{
        switch (methodSwitch){
            case "Point":
                //Do sth
                break;
            case "EndPoint":
                repc.setCanvas(canvas);
                repc.clearCanvas();
                repc.drawComponents();
                repc.drawLines(start, reset, iterations, delay, probability, endpoint, random, middle);
                break;
            case "MiddlePoint":
                //Do Sth
                break;
        }
    }

    @FXML
    public void reset(ActionEvent e) throws IOException{
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        switch (methodSwitch){
            case "Point":
                //Do sth
                break;
            case "EndPoint":
                repc.setCanvas(canvas);
                repc.drawComponents();
                break;
            case "MiddlePoint":
                //Do Sth
                break;
        }
    }

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
    public void handleMiddlePoint(ActionEvent e) throws IOException{ //1/2
        methodSwitch = "MiddlePoint";
        method.setText("Random middle point");
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        probability.setText("NaN");
        iterations.setText("1000");
        delay.setText("10");
    }

    @FXML
    public void handlePoint(ActionEvent e) throws IOException{ //1/4
        methodSwitch = "Point";
        method.setText("Random point");
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        iterations.setText("1000");
        delay.setText("10");
    }
}
