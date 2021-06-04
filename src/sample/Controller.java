package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label LabelInfo;

    private String filePath;

    @FXML
    void initialize() {





        restartStopwatch(LabelInfo);




    }


    private void restartStopwatch(Label LabelInfo) {


        Timer myTimer = new Timer();

        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Timeline timeline = new Timeline();
                KeyFrame frame = new KeyFrame(Duration.seconds(1),
                        e -> setLabel());
                timeline.getKeyFrames().add(frame);
                timeline.play();
            }
        },0,1000);
    }





    private void setLabel() {
        String filecontents = ReadFromFile.ReadFile(filePath);





        LabelInfo.setText(filecontents.trim());
        LabelInfo.setWrapText(true);
    }



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}


