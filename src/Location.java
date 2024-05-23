import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Location extends Circle {
    private String name;
    private Color circleColor = Color.BLUE;


    public Location(String name, double x, double y){
        super(x, y, 8);
        setFill(circleColor);
        this.name = name;

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);
    }

    private void handleMouseClick(MouseEvent event) {
        changeCircleColor();
    }

    private void changeCircleColor() {
        if (circleColor.equals(Color.BLUE)) {
            circleColor = Color.RED;
        } else {
            circleColor = Color.BLUE;
        }
        this.setFill(circleColor);
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name + "[" + getCenterX() + ", " + getCenterY() + "]";
    }


}