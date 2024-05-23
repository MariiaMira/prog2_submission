import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

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


        //gContext.setFill(Color.BLACK);
        gContext.fillText(name, 0, 30);
        paintCovered();

    }

    public void paintCovered(){
        gContext.setFill(Color.BLUE);
        gContext.fillOval(0,0,16,16);
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
