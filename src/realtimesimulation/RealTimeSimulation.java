

package realtimesimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class RealTimeSimulation extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        
        scene.setCursor(Cursor.NONE);
        
        stage.setScene(scene);
        stage.show();
        
        Media media = AssetManager.getBackgroundMusic();
        MediaPlayer player = new MediaPlayer(media);
        player.play();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
