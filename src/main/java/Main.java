import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Scene scene = buildScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battery Monitor");
        primaryStage.show();
    }

    public Scene buildScene(){
        BorderPane mainPane = new BorderPane();
        TabPane tabPane = new TabPane();
        Tab stats = new Tab("Statistics", loadStats());
        AnchorPane historyPane = new AnchorPane();
        Tab history = new Tab("History", historyPane);
        tabPane.getTabs().addAll(stats, history);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        mainPane.setCenter(tabPane);
        Scene scene = new Scene(mainPane, 800, 600);
        return scene;
    }

    public Pane loadStats(){
        VBox statsList = new VBox(10);
        StatisticsLoader loader = new StatisticsLoader();
        loader.load();
        HashMap<String, String> statsLoad = loader.getStats();
        ArrayList<Label> labels = new ArrayList<>();
        for(Map.Entry<String, String> entry : statsLoad.entrySet()){
            String key = entry.getKey(); String val = entry.getValue();
            switch(key){
                case "percentage":
                    labels.add(new Label("Percentage: " + val + "%"));
                    break;
                    // continue with the rest
            }
        }
        statsList.getChildren().addAll(labels);
        AnchorPane statsPane = new AnchorPane(statsList);
        return statsPane;
    }

}
