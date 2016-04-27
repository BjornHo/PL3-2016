package gui.SymanticZoom;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * An application with a zoomable and pannable canvas.
 */
@SuppressWarnings("restriction")
public class SymanticZoomTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    ArrayList<Scene> scenes = new ArrayList<Scene>();
    ArrayList<PannableCanvas> canvases = new ArrayList<PannableCanvas>();
    Scene scene;
    Scene scene2;
    Scene scene3;
    Stage globStage;
    int position = 0;
    
    @Override
    public void start(Stage stage) {

    	globStage = stage;
    	
        Group group = new Group();

        // create canvas
        PannableCanvas canvas = new PannableCanvas();

        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);

        // create sample nodes which can be dragged
        NodeGestures nodeGestures = new NodeGestures( canvas);

        Label label1 = new Label("Draggable node 1");
        label1.setTranslateX(10);
        label1.setTranslateY(10);
        label1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label2 = new Label("Draggable node 2");
        label2.setTranslateX(100);
        label2.setTranslateY(100);
        label2.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label2.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label3 = new Label("Draggable node 3");
        label3.setTranslateX(200);
        label3.setTranslateY(200);
        label3.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label3.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Circle circle1 = new Circle( 300, 300, 50);
        circle1.setStroke(Color.ORANGE);
        circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
        circle1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        circle1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Rectangle rect1 = new Rectangle(100,100);
        rect1.setTranslateX(450);
        rect1.setTranslateY(450);
        rect1.setStroke(Color.BLUE);
        rect1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        rect1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        rect1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        canvas.getChildren().addAll(label1, label2, label3, circle1, rect1);

        group.getChildren().add(canvas);

        Group group2 = new Group();

        // create canvas
        PannableCanvas canvas2 = new PannableCanvas();

        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas2.setTranslateX(100);
        canvas2.setTranslateY(100);

        // create sample nodes which can be dragged
        NodeGestures nodeGestures2 = new NodeGestures( canvas);

        Label label12 = new Label("Draggable node 1");
        label12.setTranslateX(10);
        label12.setTranslateY(10);
        label12.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label12.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label22 = new Label("Draggable node 2");
        label22.setTranslateX(100);
        label22.setTranslateY(100);
        label22.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label22.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Label label32 = new Label("Draggable node 3");
        label32.setTranslateX(200);
        label32.setTranslateY(200);
        label32.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label32.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Circle circle12 = new Circle( 300, 300, 50);
        circle12.setStroke(Color.ORANGE);
        circle12.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
        circle12.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        circle12.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        Rectangle rect12 = new Rectangle(100,100);
        rect12.setTranslateX(450);
        rect12.setTranslateY(450);
        rect12.setStroke(Color.BLUE);
        rect12.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        rect12.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        rect12.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        canvas2.getChildren().addAll(label12, label22, label32, circle12, rect12);

        group2.getChildren().add(canvas2);
        
        
        // create scene which can be dragged and zoomed
        scene = new Scene(group, 1024, 768);
        scene2 = new Scene(group2, 1024, 768);
        
        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        
        SceneGestures sceneGestures2 = new SceneGestures(canvas2);
        scene2.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene2.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene2.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        scenes.add(scene);
        scenes.add(scene2);

        globStage.setScene(scene);
        globStage.show();

        canvas.addGrid();
        canvas2.addGrid();
        canvases.add(canvas);
        canvases.add(canvas2);
    }
    
    public class SceneGestures {

        private static final double MAX_SCALE = 10.0d;
        private static final double MIN_SCALE = .1d;

        private DragContext sceneDragContext = new DragContext();

        PannableCanvas canvas;

        public SceneGestures(PannableCanvas canvas) {
            this.canvas = canvas;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        public EventHandler<ScrollEvent> getOnScrollEventHandler() {
            return onScrollEventHandler;
        }

        private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                // right mouse button => panning
                if( !event.isSecondaryButtonDown())
                    return;

                sceneDragContext.setMouseAnchorX(event.getSceneX());
                sceneDragContext.setMouseAnchorY(event.getSceneY());

                sceneDragContext.setTranslateAnchorX(canvas.getTranslateX());
                sceneDragContext.setTranslateAnchorY(canvas.getTranslateY());

            }

        };

        private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                // right mouse button => panning
                if( !event.isSecondaryButtonDown())
                    return;

                canvas.setTranslateX(sceneDragContext.getTranslateAnchorX() + event.getSceneX() - sceneDragContext.getMouseAnchorX());
                canvas.setTranslateY(sceneDragContext.getTranslateAnchorY() + event.getSceneY() - sceneDragContext.getMouseAnchorY());

                event.consume();
            }
        };

        /**
         * Mouse wheel handler: zoom to pivot point
         */
        private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent event) {
            	boolean changed = false;
                double delta = 1.2;

                double scale = canvas.getScale(); // currently we only use Y, same value is used for X
                double oldScale = scale;
                
                if (event.getDeltaY() < 0) {
                     scale /= Math.pow(delta, -event.getDeltaY()/20);
                } else {
                     scale *= Math.pow(delta, event.getDeltaY()/20);
                }

                scale = clamp( scale, MIN_SCALE, MAX_SCALE);
                double zoom = scale/MAX_SCALE;
                System.out.println("Zoom percentage: " + zoom);
                
                if(zoom == 1.0 && position < scenes.size() && position >= 0) {
                	position++;
                	changed = true;
                }
                else if(zoom == .01 && position < scenes.size() && position > 0) {
                	position--;
                	changed = true;
                }
                
                double f = (scale / oldScale)-1;

                double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
                double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));

                canvas.setScale( scale);
                canvas.setPivot(f*dx, f*dy);
                
                if(changed == true) {
                	System.out.println("Switching to canvas #" + position);
                	canvas = canvases.get(position);
                	System.out.println("Switching to scene #" + position);
                	globStage.setScene(scenes.get(position));
                }
                
                event.consume();

            }

        };


        public double clamp( double value, double min, double max) {

            if( Double.compare(value, min) < 0)
                return min;

            if( Double.compare(value, max) > 0)
                return max;

            return value;
        }
    }
}


