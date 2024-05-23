import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

public class PathFinder extends Application {
    private BorderPane root;
    private VBox top;

    private ImageView imageView;
    private Graph<Location> graph = new ListGraph<>();
    private Pane pane;
    private FlowPane buttonList;
    private Button newPlace;
    private boolean saved = true;

    @Override
    public void start(Stage stage) {
        stage.setTitle("PathFinder");
        root = new BorderPane();
        root.setPrefWidth(650);
        pane = new Pane();
        pane.setId("outputArea");
        imageView = new ImageView();
        root.setCenter(pane);

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
            pane.getChildren().clear();
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
        save.setOnAction(actionEvent -> {
            save();
        });
        MenuItem saveImage = new MenuItem();
        saveImage.setText("Save Image");
        saveImage.setId("menuSaveImage");
        saveImage.setOnAction(new SaveImageHandler());

        MenuItem exit = new MenuItem();
        exit.setText("Exit");
        exit.setId("menuExit");
        exit.setOnAction(actionEvent -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        stage.setOnCloseRequest(new ExitHandler());

        fileMenu.getItems().addAll(newMap, open, save, saveImage, exit);

        buttonList = new FlowPane();
        buttonList.setAlignment(Pos.CENTER);
        buttonList.setStyle("-fx-font-size:14");
        buttonList.setHgap(10);

        Button findPath = new Button("Find Path");
        findPath.setId("btnFindPath");
        Button showCon = new Button("Show Connection");
        showCon.setId("btnShowConnection");

        newPlace = new Button("New Place");
        newPlace.setId("btnNewPlace");
        newPlace.setOnAction(actionEvent -> {
            newPlace.setDisable(true);
            pane.setCursor(Cursor.CROSSHAIR);
            pane.setOnMouseClicked(new NewPlaceClickHandler());
        });

        Button newCon = new Button("New Connection");
        newCon.setId("btnNewConnection");
        Button changeCon = new Button("Change Connection");
        changeCon.setId("btnChangeConnection");

        buttonList.getChildren().addAll(findPath, showCon, newPlace, newCon, changeCon);
        enableButtons(false); // Disables buttons in buttonList

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
        pane.getChildren().add(imageView);
        imageView.setDisable(true);
        enableButtons(true);
        //pane = new Pane(imageView);
        //root.setCenter(pane);
    }

    private void enableButtons(boolean b){
        buttonList.getChildren().forEach(node -> node.setDisable(!b));
    }





    private void open() {

        //Check om tidigare har sparats.
        if(!saved){
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("WARNING!");
            warning.setHeaderText("Unsaved changes, continue anyway?");
            Optional<ButtonType> answer = warning.showAndWait();
            if(answer.isPresent() && answer.get() == ButtonType.CANCEL){
                return;
            }
            if(answer == null){
                return;
            }
        }
        pane.getChildren().clear();
        saved = false;
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
                //location.setOnMouseClicked(new ClickHandler());
                location.setId(name);
                graph.add(location);
                pane.getChildren().add(location);
                location.relocate(x-8,y-8);

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
            reader.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void save(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("test.graph"))){
            writer.write("file:europa.gif\n");
            for(Location location : graph.getNodes()) {
                writer.write(location.getName() + ";" + location.getX() + ";" + location.getY() + ";");
            }
            writer.newLine();
            for (Location location : graph.getNodes()){
                for (Edge<Location> edge : graph.getEdgesFrom(location)){
                    writer.write(location.getName() + ";" + edge.getDestination().getName() + ";" + edge.getName() + ";" + edge.getWeight() + "\n");
                }
            }
            saved = true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    class ExitHandler implements EventHandler<WindowEvent>{

        @Override
        public void handle(WindowEvent windowEvent) {
            if(!saved){
                Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
                warning.setTitle("WARNING!");
                warning.setHeaderText("Unsaved changes, exit anyway?");
                Optional<ButtonType> answer = warning.showAndWait();
                if (answer.isPresent() && answer.get().equals(ButtonType.CANCEL)){
                    windowEvent.consume();
                }
            }
        }
    }

    class SaveImageHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            try{
                WritableImage image = pane.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            } catch(IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "IO-fel " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    class NewPlaceClickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            FlowPane fp = new FlowPane();
            Label instruction = new Label("Name of place:");
            instruction.setStyle("-fx-font-size:14");
            TextField textField = new TextField();
            textField.setPrefWidth(200);

            fp.getChildren().addAll(instruction, textField);
            fp.setOrientation(Orientation.HORIZONTAL);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Name");
            alert.setHeaderText(null);
            fp.setAlignment(Pos.CENTER);
            fp.setHgap(30);

            alert.getDialogPane().setContent(fp);
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                String placeName = textField.getText().trim();
                if (!placeName.isEmpty()) {
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();

                    // create a new Location
                    Location newLocation = new Location(placeName, x, y);

                    newLocation.setId(placeName);

                    // add it to the graph
                    graph.add(newLocation);

                    // Add it to the pane
                    pane.getChildren().add(newLocation);
                    newLocation.relocate(x - 10, y - 10);

                    saved = false; // changes made without saving
                } /*else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Place name is empty."); //ska vi kolla att fältet innehåller något?
                    errorAlert.showAndWait();
                }*/
            }
            newPlace.setDisable(false);
            pane.setCursor(Cursor.DEFAULT);
            pane.setOnMouseClicked(null);
        }

    }

    /*class ClickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            Location element = (Location) mouseEvent.getSource();
            element.paintCovered(Color.valueOf("0xff0000ff"));
        }
    }*/

}