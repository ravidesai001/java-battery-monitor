import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        scene.getStylesheets().add(this.getClass().getResource("main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battery Monitor");
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(200);
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
        Scene scene = new Scene(mainPane, 480, 360);
        return scene;
    }

    public Pane loadStats(){
        VBox statsList = new VBox(10);
        StatisticsLoader loader = new StatisticsLoader();
        loader.load();
        HashMap<String, String> statsLoad = loader.getStats();
        ArrayList<Label> labels = new ArrayList<>();
        float fullCapacity = 0; float designCapacity = 0;
        for(Map.Entry<String, String> entry : statsLoad.entrySet()){
            String key = entry.getKey(); String val = entry.getValue();
            switch(key){
                case "percentage":
                    labels.add(new Label("Percentage: " + val + "%"));
                    break;
                case "discharge":
                    labels.add(new Label("Discharge Rate: " + val + "W"));
                    break;
                case "full_capacity":
                    fullCapacity = Integer.parseInt(val)/ (float)1000000;
                    labels.add(new Label("Full Capacity: " + fullCapacity + "Wh"));
                    break;
                case "design_capacity":
                    designCapacity = Integer.parseInt(val)/ (float)1000000;
                    labels.add(new Label("Design Capacity: " + designCapacity + "Wh"));
                    break;
            }
        }
        int health = Math.round(fullCapacity/(float)designCapacity * 100);
        labels.add(new Label("Battery Health: " + health + "%"));
        statsList.getChildren().addAll(labels);
        AnchorPane statsPane = new AnchorPane(statsList);
        return statsPane;
    }

}
