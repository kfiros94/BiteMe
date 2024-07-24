package LogInAnimaPhotos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.util.Random;

public class LoginPageController1 {

    @FXML
    private Pane snowflakePane;

    @FXML
    private ImageView imageView;
    
    
    @FXML
    private MediaView MediaView;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int NUM_IMAGES = 2;

    private static final String[] IMAGE_FILES = {
        "/images/a.png"
    };

    @FXML
    public void initialize() {
        // Initialize the video
        String videoFile = getClass().getResource("b.mp4").toExternalForm();
        Media media = new Media(videoFile);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView.setMediaPlayer(mediaPlayer);
     //   mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // Initialize the falling images
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            for (int i = 0; i < NUM_IMAGES; i++) {
             //   ImageView imageView = new ImageView(getRandomImage());
                imageView.setX(new Random().nextInt(WIDTH));
                imageView.setY(0);
                //snowflakePane.getChildren().add(imageView);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(5 + new Random().nextInt(10)), imageView);
                tt.setToY(HEIGHT);
               // tt.setOnFinished(e -> snowflakePane.getChildren().remove(imageView));
                tt.play();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private Image getRandomImage() {
        String imageFile = IMAGE_FILES[new Random().nextInt(IMAGE_FILES.length)];
        return new Image(getClass().getResourceAsStream(imageFile));
    }
}
