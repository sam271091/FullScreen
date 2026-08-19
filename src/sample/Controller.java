package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    @FXML
    private HBox header;

    @FXML
    private Label cardNumber;

    @FXML
    private HBox headerDate;

    @FXML
    private Label currDate;

    @FXML
    private VBox footer;


    private String filePath;

    private String videoFilePath;

    private MediaPlayer mediaPlayer;

    private Media media;

    private ObservableList<Row> rowsData = FXCollections.observableArrayList();

    private LocalDateTime currentDate;

    @FXML
    private TableView<Row> itemsTable;

    @FXML
    private TableColumn<Row, Integer> colNo;

    @FXML
    private TableColumn<Row, String> itemCol;

    @FXML
    private TableColumn<Row, Double> quantityCol;

    @FXML
    private TableColumn<Row, Double> priceCol;

    @FXML
    private TableColumn<Row, Double> sumCol;


    @FXML
    private VBox mainBox;

    @FXML
    private VBox mainScreen;

    @FXML
    private ImageView IOS_PIC;

    @FXML
    private ImageView AND_PIC;

    @FXML
    private ImageView LOGO;


    @FXML
    private Label labelEndOfSale;

    private  DoubleProperty width;
    private  DoubleProperty height;


    private int counter;


    private boolean isPlaying;

    List videoFilesList;

    private Integer lastPlayedVideoFile;
    Boolean firstMediaInit;

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public void stopmediaPlayer(){
        mediaPlayer.stop();


        width.unbind();
        height.unbind();

    }


    List<String> getVideoFilesList() throws IOException{
        try (Stream<Path> stream = Files.list(Paths.get(videoFilePath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file)
                    && file.getFileName().toString().endsWith(".mp4"))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }


    @FXML
    void initialize() {


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        currentDate = LocalDateTime.now();


        isPlaying = false;

        colNo.setCellValueFactory(new PropertyValueFactory<Row, Integer>("num"));
        itemCol.setCellValueFactory(new PropertyValueFactory<Row, String>("Item"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Row, Double>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Row, Double>("price"));
        sumCol.setCellValueFactory(new PropertyValueFactory<Row, Double>("sum"));

        quantityCol.setCellFactory(column -> new TableCell<Row, Double>() {
            private final DecimalFormat df = new DecimalFormat("0.##");

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : df.format(value) + " əd");
            }
        });

        sumCol.setCellFactory(column -> new TableCell<Row, Double>() {
            private final DecimalFormat df = new DecimalFormat("0.00");

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : df.format(value));
            }
        });

        itemsTable.setItems(rowsData);

        itemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        restartStopwatch(LabelInfo);


         width = mediaView.fitWidthProperty();
         height = mediaView.fitHeightProperty();


        currDate.setText(dtf.format(currentDate));



        LOGO.setImage(new Image("/sample/logo_pic.png"));


        itemsTable.setMaxWidth(1f * Integer.MAX_VALUE * 70);


        itemsTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        colNo.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // 50% width
        itemCol.setMaxWidth( 1f * Integer.MAX_VALUE * 70 ); // 30% width
        quantityCol.setMaxWidth( 1f * Integer.MAX_VALUE * 8 ); // 20% width
        priceCol.setMaxWidth( 1f * Integer.MAX_VALUE * 8 ); // 20% width
        sumCol.setMaxWidth( 1f * Integer.MAX_VALUE * 9 ); // 20% width



        itemsTable.setPrefHeight(1f * Integer.MAX_VALUE * 70);


        itemsTable.getItems().addListener(new ListChangeListener<Row>() {
            @Override
            public void onChanged(Change<? extends Row> c) {
                itemsTable.scrollTo(c.getList().size()-1);
            }
        });

    }




    private void initData(Row row) {
        rowsData.add(row);
    }



    void initializeMedia(){
        disposePlayer();
        if (firstMediaInit){
            lastPlayedVideoFile = 0;
            firstMediaInit = false;
        } else {
            if (lastPlayedVideoFile >= videoFilesList.size()-1){
                lastPlayedVideoFile = 0;
            } else {
                lastPlayedVideoFile = ++lastPlayedVideoFile;
            }

        }

        String CurrentFile = videoFilesList.get(lastPlayedVideoFile).toString();


        media = new Media(new File(CurrentFile.toString()).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnEndOfMedia(this::initializeMedia);

        playVideo();

        mediaView.setMediaPlayer(mediaPlayer);

    }


    public void initializePlayer(){

        try {
            videoFilesList = getVideoFilesList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initializeMedia();

    }


    private void disposePlayer() {
        MediaPlayer player = mediaView.getMediaPlayer();
        if (player != null) {
            player.dispose(); // release resources
        }
    }


    private void playVideo(){


        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));



        mediaPlayer.play();

        mediaView.setVisible(true);

        isPlaying = true;

    }


    private void restartStopwatch(Label LabelInfo) {


//        Timer myTimer = new Timer();
//
//        myTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Timeline timeline = new Timeline();
//                KeyFrame frame = new KeyFrame(Duration.seconds(1),
//                        e -> setLabel());
//                timeline.getKeyFrames().add(frame);
//                timeline.play();
//            }
//        },0,1000);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> setLabel()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }





    private void setLabel() {
        String filecontents = ReadFromFile.ReadFile(filePath);



        JSONObject jobject = new JSONObject(filecontents.trim());

        JSONArray rows = jobject.getJSONArray("rows");


        counter++;


        if (rows.length() ==0){



            if (counter >3){

                fillIndata(jobject,rows);

                if (! isPlaying){
                    playVideo();
                }
                itemsTable.setVisible(false);
                header.setVisible(false);
                headerDate.setVisible(false);
                footer.setVisible(false);
//            resultLabel.setVisible(false);
                discountLabel.setVisible(false);
                labelTotal.setVisible(false);

                mainScreen.setVisible(false);

                mainBox.setVisible(false);
            } else {
                labelEndOfSale.setVisible(true);
            }



        } else {

            fillIndata(jobject,rows);

            counter = 0;

            labelEndOfSale.setVisible(false);

            mainBox.setVisible(true);
            mainScreen.setVisible(true);
            mediaView.setVisible(false);
            itemsTable.setVisible(true);

            header.setVisible(true);
            headerDate.setVisible(true);
            footer.setVisible(true);
//            resultLabel.setVisible(true);
            discountLabel.setVisible(true);
            labelTotal.setVisible(true);

//            mediaPlayer.stop();
            stopmediaPlayer();
            isPlaying = false;

        }


    }



    private void fillIndata(JSONObject jobject,JSONArray rows){




        Double result = jobject.getDouble("result");

        Double discount = jobject.getDouble("discount");

        Double total = jobject.getDouble("total");

        String CNumber = jobject.getString("cardNumber");

        rowsData.clear();

        StringBuilder rowString = new StringBuilder();
        for (int i =0;i<rows.length();i++) {
            JSONObject row = (JSONObject) rows.get(i);
            initData(new Row(row.getInt("num"),row.getString("item").toString(),row.getDouble("quantity"),row.getDouble("price"),row.getDouble("sum")));

        }


//        cardNumber.setText(CNumber.toString());
//        cardNumber.setWrapText(true);
//
        DecimalFormat df = new DecimalFormat("0.00");

        discountLabel.setText(df.format(discount));



//        discountLabel.setWrapText(true);


        labelTotal.setText(Double.toString(total));
        labelTotal.setWrapText(true);
    }



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}


