import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class Location extends Canvas {
    private String name;
    private double x;
    private double y;
    private GraphicsContext graphicsContext = getGraphicsContext2D();

    public Location(String name, double x, double y){
        super(60, 60);
        this.name = name;
        this.x = x;
        this.y = y;
        //gContext.setFill(Color.BLACK);
        graphicsContext.fillText(name, 0, 30);
        graphicsContext.setTextAlign(TextAlignment.JUSTIFY);
        paintCovered(Color.BLUE);

    }

    public void paintCovered(Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillOval(0,0,16,16);
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
