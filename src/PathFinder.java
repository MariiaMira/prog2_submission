import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class PathFinder extends Application {
    private BorderPane root;
    private VBox top;

    private ImageView imageView;
    private ListGraph<Location> graph;
    private FlowPane pane;
    private boolean saved = false;

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
        open.setOnAction(actionEvent -> {
            open();
            stage.sizeToScene();
        });
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
        //Check om tidigare har sparats.
        if(saved == false){
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("WARNING!");
            warning.setHeaderText("Unsaved changes, continue anyway?");
            Optional<ButtonType> answer = warning.showAndWait();
            if(answer.isPresent() && (answer.get() == ButtonType.CANCEL)){
                System.out.println("Tryck på cancel");
                return;
            }
            if(answer == null){
                return;
            }
        }

        graph = new ListGraph<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("europa.graph"));
            String fileName = reader.readLine();
            loadMap(fileName);

            String line = reader.readLine();
                String[] parts = line.split(";");
                for(int i = 0; i<parts.length;i += 3){
                    String name = parts[i];
                    double x = Double.parseDouble(parts[i+1]);
                    double y = Double.parseDouble(parts[i+2]);
                    Location location = new Location(name, x, y);
                    graph.add(location);
                }


            while((line = reader.readLine()) != null){
                String[] edgeData = line.split(";");

                Location from = null;
                Location to = null;
                for(Location l : graph.getNodes()){
                    if(edgeData[0].equals(l.getName())){
                        from = l;
                    }
                    if(edgeData[1].equals(l.getName())){
                        to = l;
                    }
                }

                String medium = edgeData[2];
                int distance = Integer.parseInt(edgeData[3]);

                if(from != null && to != null && !graph.pathExists(from, to)) {
                        graph.connect(from, to, medium, distance); }
            }
            System.out.println(graph.getNodes());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}