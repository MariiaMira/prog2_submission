import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Location extends VBox {
    private String name;
    private double x;
    private double y;
    private Circle circle;
    private Label label;

    public Location(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;
        //VBox container = new VBox();
        this.circle = new Circle(8,Color.BLUE);
        this.label = new Label(name);
        getChildren().addAll(circle, label);
        //setLayoutX(x);
        //setLayoutY(y);


        //container.getChildren().addAll(circle, label);
        //container.setOnMouseClicked(new clickHandler());
    }

    public String getName(){
        return name;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public String toString(){
        return name + "[" + x + ", " + y + "]";
    }

    // förändra färg vid klick
    class clickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            circle.setFill(Color.RED);
            requestFocus();
        }
    }

}
