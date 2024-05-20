import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.*;

public class PathFinder extends Application {
    private BorderPane root;
    private VBox top;

    private ImageView imageView;
    private ListGraph<String> graph;
    private FlowPane pane;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setPrefWidth(650);
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);
        top = new VBox();

        VBox menu = new VBox();
        MenuItem newMap = new MenuItem();
        newMap.setText("New Map");
        newMap.setOnAction(actionEvent -> {
            loadMap("file:europa.gif");
            graph = new ListGraph<>();
            stage.sizeToScene();
        });

        MenuItem open = new MenuItem();
        open.setText("Open");
        MenuItem save = new MenuItem();
        save.setText("Save");
        MenuItem saveImage = new MenuItem();
        saveImage.setText("Save Image");
        MenuItem exit = new MenuItem();
        exit.setText("Exit");
        fileMenu.getItems().addAll(newMap, open, save, saveImage, exit);


        FlowPane buttonList = new FlowPane();
        buttonList.setAlignment(Pos.CENTER);
        buttonList.setStyle("-fx-font-size:14");
        buttonList.setHgap(10);

        Button findPath = new Button("Find Path");
        Button showCon = new Button("Show Connection");
        Button newPlace = new Button("New Place");
        Button newCon = new Button("New Connection");
        Button changeCon = new Button("Change Connection");
        buttonList.getChildren().addAll(findPath, showCon, newPlace, newCon, changeCon);

        top.getChildren().addAll(menuBar, buttonList);
        root.setTop(top);
        imageView = new ImageView();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    private void loadMap(String fileName) {
        Image mapImage = new Image(fileName);
        imageView.setImage(mapImage);
        pane = new FlowPane(imageView);
        root.setCenter(pane);

    }

    private void open() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("europa.graph"));
            String fileName = reader.readLine();
            loadMap(fileName);

            String line;
            for((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                for(int i = 0; i<parts.length;i += 3){
                    String name = parts[i];
                    double x = Double.parseDouble(parts[i+1]);
                    double y = Double.parseDouble(parts[i+2]);
                    graph.add(name);



                }
            }




        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}