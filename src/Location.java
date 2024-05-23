import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Location extends Canvas {
    private String name;
    private double x;
    private double y;
    private Circle circle;
    private Label label;
    private GraphicsContext gContext = getGraphicsContext2D();

    public Location(String name, double x, double y){
        super(75, 75);
        this.name = name;
        this.x = x;
        this.y = y;
        this.circle = new Circle(8,Color.BLUE);
        this.label = new Label(name);
        //getChildren().addAll(circle, label);
        paintCovered();
        gContext.fillOval(0,0,16,16);
        gContext.setFill(Color.BLACK);
        gContext.fillText(name, 0, 30);

    }

    public void paintCovered(){
        gContext.setFill(Color.BLUE);
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
