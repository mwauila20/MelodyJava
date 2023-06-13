import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
//import java.awt.Color;
import javafx.scene.layout.Background;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
//import java.awt.Paint;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import  javafx.scene.layout.GridPane;
public class Client extends Application {
    private static final String SERVER_IP = "127.0.0.1"; // IP-адрес сервера
    private static final int SERVER_PORT = 8888; // Порт сервера
    private static String SONG_PATH = "songs\\";
    
 ImageView imageView = new ImageView();
 String cssDefault = "-fx-border-color: blue;\n" + "-fx-border-insets: 5;\n"
        + "-fx-border-width: 3;\n";
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Label guessLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label resultLabel;
    private int time_sec;
    private String lastPlayedSong;
    private Button song1Button;
    private Button song2Button;
    private Button song3Button;
    private Button song4Button;
    private Button song5Button;
    private Button song6Button;
    private Button song7Button;
    private Button startButton;
    private Button nextButton;
//    private Button submitButton;
    
    
    private Timer myTimer;
    private MediaPlayer mediaPlayer;
private static final Color[] COLORS = {
            Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.PURPLE, Color.YELLOW
    };
private static final int TRANSITION_DURATION = 2000;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
         Image image = new Image("file:K:/kursjava67567567/Kurs/songs/Hello.jpg");
         final HBox pictureRegion = new HBox();
           GridPane gridpane = new GridPane();
    pictureRegion.setStyle(cssDefault);
    pictureRegion.getChildren().add(imageView);
    gridpane.add(pictureRegion, 1, 1);
        imageView.setImage(image);
        imageView.setFitWidth(250); 
        imageView.setFitHeight(300); 
        imageView.setStyle(cssDefault);
        guessLabel = new Label("Угадай мелодию");
        guessLabel.setStyle("-fx-font-size: 32px;");    
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 15px;");
        scoreLabel.setLayoutY(200);
        scoreLabel.setLayoutX(200);
        timeLabel = new Label("Time: 0");
        timeLabel.setStyle("-fx-font-size: 15px;");
        resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-font-size: 15px;");
        startButton = new Button("Start Game");
        startButton.setOnAction(event -> handleStartGame());
        startButton.setStyle("-fx-background-color: yellow;-fx-font-size: 15px;");

        nextButton = new Button("Next Song");
        nextButton.setOnAction(event -> handleNextSong());
        nextButton.setStyle("-fx-background-color: yellow;");

//        submitButton = new Button("Submit Answer");
//        submitButton.setOnAction(event -> handleSubmitAnswer());
        
        song1Button = new Button("song1");
        song1Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        //song1Button.setBackground(Color.BLUE);
        song1Button.setOnAction(event -> handleSong1Answer());
        song1Button.setDisable(true);
        
        song2Button = new Button("song2");
        song2Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song2Button.setOnAction(event -> handleSong2Answer());
        song2Button.setDisable(true);
        
        song3Button = new Button("song3");
        song3Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song3Button.setOnAction(event -> handleSong3Answer());
        song3Button.setDisable(true);
        
        song4Button = new Button("song4");
        song4Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song4Button.setOnAction(event -> handleSong4Answer());
        song4Button.setDisable(true);
        
        song5Button = new Button("song5");
        song5Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song5Button.setOnAction(event -> handleSong5Answer());
        song5Button.setDisable(true);
        
        song6Button = new Button("song6");
        song6Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song6Button.setOnAction(event -> handleSong6Answer());
        song6Button.setDisable(true);
        
        song7Button = new Button("song7");
        song7Button.setStyle("-fx-background-color: blue; -fx-text-fill: white;-fx-font-size: 15px;");
        song7Button.setOnAction(event -> handleSong7Answer());
        song7Button.setDisable(true);

        VBox root = new VBox(15);
        root.getChildren().add(guessLabel);
        root.getChildren().add(gridpane);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(scoreLabel, timeLabel, startButton, nextButton, song1Button, song2Button, song3Button,song4Button,song5Button,song6Button,song7Button, resultLabel);
        root.setStyle("-fx-background-color: orange;");
        
        

        Scene scene = new Scene(root, 285, 800);
        
       // scene.setBackgroundColor(Color.parseColor("#ffffff"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Guess the Melody");
        primaryStage.show();
        animateGradientLabel(guessLabel);
        connectToServer();
    }
 private void animateGradientLabel(Label label) {
        // Создание FadeTransition для плавного перехода между цветами
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(TRANSITION_DURATION), label);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        // Создание Timeline для изменения цвета надписи
        Timeline colorTimeline = new Timeline();
        KeyFrame[] keyFrames = new KeyFrame[COLORS.length];
        for (int i = 0; i < COLORS.length; i++) {
            Color color = COLORS[i];
            Stop[] stops = {
                    new Stop(0, color),
                    new Stop(1, color.darker()) // Темнее значение цвета для создания градиента
            };
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            keyFrames[i] = new KeyFrame(Duration.millis(i * TRANSITION_DURATION), event -> {
                Paint paint = gradient;
                label.setTextFill(paint);
            });
        }
        colorTimeline.getKeyFrames().addAll(keyFrames);
        colorTimeline.setCycleCount(Animation.INDEFINITE);

        // Запуск анимации
        fadeTransition.play();
        colorTimeline.play();
    }


 
    private void connectToServer() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            Thread readerThread = new Thread(this::readServerData);
            readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    private void readServerData() {
        try {
            String serverData;
            while ((serverData = reader.readLine()) != null) {
                final String data = serverData; // Создаем финальную копию serverData
                Platform.runLater(() -> handleServerData(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSongFile(String songName) {
        // Получение пути к файлу песни по ее имени из вашей локальной директории
        // Здесь нужно реализовать логику поиска пути к файлу песни на вашей системе
        return  (SONG_PATH + songName);
    }
    
    private void playSong(String songFile) {
    if (mediaPlayer != null) {
        mediaPlayer.stop();
    }

    Media media = new Media(new File(songFile).toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
    }

    private void handleServerData(String serverData) {
        // Обработка данных от сервера и обновление интерфейса

        // Пример:
     
        if ((serverData.startsWith("CORRECT")) || (serverData.startsWith("ERROR")) || (serverData.startsWith("TIMEOUT"))) {
            Platform.runLater(() -> resultLabel.setText("Result: " + serverData));
        }       
        else if (serverData.startsWith("SCORE: ")) {
            String scoreValue = serverData.substring(7);
            Platform.runLater(() -> scoreLabel.setText("Score: " + scoreValue));
            
        } else if (serverData.startsWith("TIME: ")) {
            String timeValue = serverData.substring(6);
            Platform.runLater(() -> timeLabel.setText("Time: " + timeValue));
            
        } else if (serverData.startsWith("SONGS: ")) { 
            String[] parts = (serverData.substring(7)).split(",");
            song1Button.setText(parts[0]);
            song2Button.setText(parts[1]);
            song3Button.setText(parts[2]);
            song4Button.setText(parts[3]);
            song5Button.setText(parts[4]);
            song6Button.setText(parts[5]);
            song7Button.setText(parts[6]);
            // Обновить название песни в интерфейсе
            
        } else if (serverData.startsWith("PLAY_SONG: ")) { 
            String songName = serverData.substring(11);
            if (!songName.equals(lastPlayedSong)) {
            lastPlayedSong = songName;    
            changeImage(songName);
            myTimer = new Timer();
            time_sec = 5;
            playSong(getSongFile(songName));
            myTimer.schedule(new StopTask(), 0, 1000);
            song1Button.setDisable(false);
            song2Button.setDisable(false);
            song3Button.setDisable(false);
            song4Button.setDisable(false);
            song5Button.setDisable(false);
            song6Button.setDisable(false);
            song7Button.setDisable(false);
            }
            else {
        // Песня уже была воспроизведена, перейти к следующей
        //writer.println("NEXT");
    }
        }
        
        
    }
    
    class StopTask extends TimerTask {
        public void run() {
            time_sec --;
            String timeValue = Integer.toString(time_sec);
            Platform.runLater(() -> timeLabel.setText("Time: " + timeValue));
            if (time_sec == 0)
            {
                writer.println("TIMEOUT");
                song1Button.setDisable(true);
                song2Button.setDisable(true);
                song3Button.setDisable(true);
                song4Button.setDisable(true);
                song5Button.setDisable(true);
                song6Button.setDisable(true);
                song7Button.setDisable(true);
                myTimer.cancel();
            }
        }
    }
private void changeImage(String songId) {
    Image image;
     //Rectangle border = new Rectangle(250, 300);
       // border.setFill(Color.BLUE);
       // border.setStroke(Color.BLACK);
        //border.setStrokeWidth(4);
    switch (songId) {
        case "Katy Perry - Hot n Cold.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Katy.jpg");
            break;
        case "Kendrick Lamar - HUMBLE.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Kendrick.jpg");
            break;
        case "Lil Nas X - Old Town Road.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Lil Nas X.jpg");
            break;
        case "Polo G-Finer Things.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Polo G.jpg");
            break;
        case "Taylor Swift - Blank Space.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Taylor Swift.png");
            break;
        case "Travis Scott and Drake - Sicko Mode.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Travis Scott.jpg");
            break;
        case "Wiz Khalifa-Black and Yellow.wav":
            image = new Image("file:K:/kursjava67567567/Kurs/songs/Wiz Khalifa.jpg");
            break;
        default:
            // Если передан неправильный идентификатор песни, установите
            // изображение по умолчанию или выполните другие действия
            image = new Image("file:K:/kursjava67567567/Kurs/songs/LeBron.jpg");
            break;
    }
    imageView.setImage(image);
   // imageView.setClip(border);
}
    private void handleStartGame() {
        writer.println("START");
    }

    private void handleNextSong() {
        resultLabel.setText("");
        writer.println("NEXT");
    }

//    private void handleSubmitAnswer() {
//        myTimer.cancel();
//        String answer = answerField.getText();
//        writer.println("ANSWER: " + answer);
//    }
    
    private void handleSong1Answer() {
        myTimer.cancel();
        String answer = song1Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    }
    
    private void handleSong2Answer() {
        myTimer.cancel();
        String answer = song2Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    }
    
    private void handleSong3Answer() {
        myTimer.cancel();
        String answer = song3Button.getText();
        writer.println("ANSWER: " + answer);
       song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);;
    }
private void handleSong4Answer() {
        myTimer.cancel();
        String answer = song4Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    } 
private void handleSong5Answer() {
        myTimer.cancel();
        String answer = song5Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    }    
private void handleSong6Answer() {
        myTimer.cancel();
        String answer = song6Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    }    
private void handleSong7Answer() {
        myTimer.cancel();
        String answer = song7Button.getText();
        writer.println("ANSWER: " + answer);
        song1Button.setDisable(true);
        song2Button.setDisable(true);
        song3Button.setDisable(true);
        song4Button.setDisable(true);
        song5Button.setDisable(true);
        song6Button.setDisable(true);
        song7Button.setDisable(true);
    }    
}