package sample;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label LabelInfo;

    @FXML
    private Label labelTotal;

    @FXML
    private MediaView mediaView;


    private String filePath;

    private MediaPlayer mediaPlayer;


    public void stopmediaPlayer(){
        mediaPlayer.stop();
    }

    @FXML
    void initialize() {





        restartStopwatch(LabelInfo);


        Media media = new Media(new File("C:\\Users\\Администратор\\Desktop\\ad.mp4").toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.getOnRepeat();


        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

        mediaView.setPreserveRatio(true);


        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });

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

        JSONObject jobject = new JSONObject(filecontents);

        JSONArray rows = jobject.getJSONArray("rows");

        Double total = jobject.getDouble("Total");

        StringBuilder rowString = new StringBuilder();
        for (int i =0;i<rows.length();i++) {
            String row = rows.get(i).toString();

            rowString.append(row.toString() + "\n");
        }

        LabelInfo.setText(rowString.toString());
        LabelInfo.setWrapText(true);


        labelTotal.setText(Double.toString(total));
        labelTotal.setWrapText(true);
    }



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}


