import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Location extends Group {
    private String name;
    private double x;
    private double y;
    private Circle circle;
    private Label label;

    private Color circleColor = Color.BLUE;


    public Location(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;

        this.circle = new Circle(8, circleColor);
        this.label = new Label(name);
        label.layoutXProperty().bind(circle.centerXProperty().subtract(label.widthProperty().divide(2)));
        label.setLayoutY(10);

        this.getChildren().addAll(circle, label);

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
    /*class clickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent mouseEvent) {
            circle.setFill(Color.RED);
            requestFocus();
        }
    }
    */

}