import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Location extends Canvas {
    private String name;
    private double x;
    private double y;
    private Circle circle;
    private Label label;
    private GraphicsContext graphicsContext = getGraphicsContext2D();

    public Location(String name, double x, double y){
        super(75, 75);
        this.name = name;
        this.x = x;
        this.y = y;
        this.circle = new Circle(8, Color.BLUE);
        this.label = new Label(name);

        // Draw the name and cover circle
        graphicsContext.fillText(name, 0, 30);
        paintCovered();
    }

    public void paintCovered(){
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillOval(0, 0, 16, 16);
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

    @Override
    public String toString(){
        return name + "[" + x + ", " + y + "]";
    }
}
