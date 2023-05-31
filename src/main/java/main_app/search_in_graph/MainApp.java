package main_app.search_in_graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import main_app.search_in_graph.MainController;

public class MainApp extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 440);
        stage.setTitle("search_in_graph");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}