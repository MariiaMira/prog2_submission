import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Location extends Circle {
    private String name;

    public Location(String name){
        this.name = name;
        VBox container = new VBox();
        Circle circle = new Circle();
        circle.setFill(Color.BLUE);
        Label label = new Label(name);
        container.getChildren().addAll(circle, label);
    }

    // förändra färg vid klick

}
