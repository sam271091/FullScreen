package sample;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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


    @FXML
    private Label resultLabel;

    @FXML
    private Label discountLabel;



    private String filePath;

    private String videoFilePath;

    private MediaPlayer mediaPlayer;

    private ObservableList<Row> rowsData = FXCollections.observableArrayList();


    @FXML
    private TableView<Row> itemsTable;

    @FXML
    private TableColumn<Row, String> itemCol;

    @FXML
    private TableColumn<Row, Double> quantityCol;

    @FXML
    private TableColumn<Row, Double> priceCol;


    private  DoubleProperty width;
    private  DoubleProperty height;


    private boolean isPlaying;

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public void stopmediaPlayer(){
        mediaPlayer.stop();


        width.unbind();
        height.unbind();

    }



    @FXML
    void initialize() {


        isPlaying = false;

//        initData();

        itemCol.setCellValueFactory(new PropertyValueFactory<Row, String>("Item"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Row, Double>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Row, Double>("price"));

        itemsTable.setItems(rowsData);

        itemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        restartStopwatch(LabelInfo);


         width = mediaView.fitWidthProperty();
         height = mediaView.fitHeightProperty();



//        if (videoFilePath != null) {
//            initializePlayer();
//        }


    }


    private void initData(Row row) {
        rowsData.add(row);
    }



    public void initializePlayer(){
        Media media = new Media(new File(videoFilePath.toString()).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

//        mediaPlayer.setAutoPlay(true);

        mediaPlayer.getOnRepeat();






        mediaView.setPreserveRatio(true);


        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
//                mediaPlayer.play();
                playVideo();
            }
        });
    }


    private void playVideo(){


        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));



        mediaPlayer.play();

        mediaView.setVisible(true);

        isPlaying = true;

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

        Double result = jobject.getDouble("result");

        Double discount = jobject.getDouble("discount");

        Double total = jobject.getDouble("total");

        rowsData.clear();

        StringBuilder rowString = new StringBuilder();
        for (int i =0;i<rows.length();i++) {
            JSONObject row = (JSONObject) rows.get(i);
//
            initData(new Row(row.getString("item").toString(),row.getDouble("quantity"),row.getDouble("price")));





//            rowString.append(row.toString() + "\n");
//            JSONObject row = jobject.getJSONObject("");

        }

        resultLabel.setText("Yekun: " + Double.toString(result));
        resultLabel.setWrapText(true);

        discountLabel.setText("Endirim: " + Double.toString(discount));
        discountLabel.setWrapText(true);


        labelTotal.setText("Ödəniləcək məbləğ: " + Double.toString(total));
        labelTotal.setWrapText(true);


        if (rows.length() ==0){
            if (! isPlaying){
                playVideo();
            }
            itemsTable.setVisible(false);
            resultLabel.setVisible(false);
            discountLabel.setVisible(false);
            labelTotal.setVisible(false);
        } else {
            mediaView.setVisible(false);
            itemsTable.setVisible(true);

            resultLabel.setVisible(true);
            discountLabel.setVisible(true);
            labelTotal.setVisible(true);

//            mediaPlayer.stop();
            stopmediaPlayer();
            isPlaying = false;

        }


//        LabelInfo.setText(rowString.toString());
//        LabelInfo.setWrapText(true);



    }



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}


