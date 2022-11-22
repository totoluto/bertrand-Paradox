package ch.kbw.totoluto.bertrandparadox;

import ch.kbw.totoluto.bertrandparadox.method.RandomEndPointController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    private String methodSwitch = "Point";
    private GraphicsContext graphicsContext;
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

                break;
            case "MiddlePoint":
                //Do Sth
                break;
        }
    }

    @FXML
    public void reset(ActionEvent e) throws IOException{
        //Clear Components
    }

    @FXML
    public void handleEndPoint(ActionEvent e) throws IOException{ //1/3
        methodSwitch = "EndPoint";
        method.setText("Random end point");
        repc.setCanvas(canvas);
        repc.drawComponents();
        //TODO: Start canvas init
    }

    @FXML
    public void handleMiddlePoint(ActionEvent e) throws IOException{ //1/2
        methodSwitch = "MiddlePoint";
        method.setText("Random middle point");
        //TODO: Start canvas init
    }

    @FXML
    public void handlePoint(ActionEvent e) throws IOException{ //1/4
        methodSwitch = "Point";
        method.setText("Random point");
        //TODO: Start canvas init
    }
}
