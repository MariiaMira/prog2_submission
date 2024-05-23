import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Location extends Canvas {
    private String name;
    private double x;
    private double y;
    private GraphicsContext graphicsContext = getGraphicsContext2D();
    private Color ovalColor = Color.BLUE;

    public Location(String name, double x, double y){
        super(60, 60);
        this.name = name;
        this.x = x;
        this.y = y;
        //gContext.setFill(Color.BLACK);
        graphicsContext.fillText(name, 0, 50);
        graphicsContext.setTextAlign(TextAlignment.JUSTIFY);
        paintCovered(ovalColor);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);
    }

    public void paintCovered(Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillOval(30,30,16,16);
    }

    private void handleMouseClick(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();

        // Calculate if the click is within the bounds of the oval centered at (8, 8) with a radius of 8
        if (Math.pow(clickX - 30, 2) + Math.pow(clickY - 30, 2) <= Math.pow(8, 2)) {
            ovalColor = ovalColor.equals(Color.BLUE) ? Color.RED : Color.BLUE;
            paintCovered(ovalColor);
        }
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
