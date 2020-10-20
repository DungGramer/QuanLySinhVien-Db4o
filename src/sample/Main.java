package sample;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Quan Ly Sinh Vien");

    primaryStage.setScene(new Scene(root, 1000, 600));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
