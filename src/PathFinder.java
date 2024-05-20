import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class PathFinder extends Application {
    private BorderPane root;
    private VBox top;

    private ImageView imageView;
    private Graph<Location> graph = new ListGraph<>();
    private Pane pane;
    private boolean saved = true;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setPrefWidth(650);
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu");
        Menu fileMenu = new Menu("File");
        fileMenu.setId("menuFile");
        menuBar.getMenus().add(fileMenu);
        top = new VBox();

        VBox menu = new VBox();
        MenuItem newMap = new MenuItem();
        newMap.setText("New Map");
        newMap.setId("menuNewMap");
        newMap.setOnAction(actionEvent -> {
            loadMap("file:europa.gif");
            graph = new ListGraph<>();
            stage.sizeToScene();
        });

        MenuItem open = new MenuItem();
        open.setText("Open");
        open.setId("menuOpenFile");
        open.setOnAction(actionEvent -> {
            open();
            stage.sizeToScene();
        });
        MenuItem save = new MenuItem();
        save.setText("Save");
        save.setId("menuSaveFile");
        MenuItem saveImage = new MenuItem();
        saveImage.setText("Save Image");
        saveImage.setId("menuSaveImage");
        MenuItem exit = new MenuItem();
        exit.setText("Exit");
        exit.setId("menuExit");
        fileMenu.getItems().addAll(newMap, open, save, saveImage, exit);


        FlowPane buttonList = new FlowPane();
        buttonList.setAlignment(Pos.CENTER);
        buttonList.setStyle("-fx-font-size:14");
        buttonList.setHgap(10);

        Button findPath = new Button("Find Path");
        findPath.setId("btnFindPath");
        Button showCon = new Button("Show Connection");
        showCon.setId("btnShowConnection");
        Button newPlace = new Button("New Place");
        newPlace.setId("btnNewPlace");
        Button newCon = new Button("New Connection");
        newCon.setId("btnNewConnection");
        Button changeCon = new Button("Change Connection");
        changeCon.setId("btnChangeConnection");
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
        pane = new Pane(imageView);
        root.setCenter(pane);

    }

    private void open() {
        //Check om tidigare har sparats.
        if(!saved){
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("WARNING!");
            warning.setHeaderText("Unsaved changes, continue anyway?");
            Optional<ButtonType> answer = warning.showAndWait();
            if(answer.isPresent() && (answer.get() == ButtonType.CANCEL)){
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
                location.setId(name);
                graph.add(location);
                pane.getChildren().add(location);
                location.relocate(x -10,y-10);
                //location.setLayoutX(x);
                //location.setLayoutY(y);
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
            //System.out.println(graph.getNodes());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}