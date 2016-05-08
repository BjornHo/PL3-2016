package gui.phylogeny;

import javafx.scene.Group;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

public class NewickEdge extends Group {
	
	private Line vertical = new Line();
	private Line horizontal = new Line();
	
	
	
	public NewickEdge(NewickNode src, NewickNode dst) {
		
		vertical.startXProperty().bindBidirectional(src.getXProperty());
		vertical.startYProperty().bindBidirectional(src.getYProperty());
		vertical.endXProperty().bindBidirectional(src.getXProperty());
		vertical.endYProperty().bindBidirectional(dst.getYProperty());
		
		horizontal.startXProperty().bindBidirectional(src.getXProperty());
		horizontal.startYProperty().bindBidirectional(dst.getYProperty());
		horizontal.endXProperty().bindBidirectional(dst.getXProperty());
		horizontal.endYProperty().bindBidirectional(dst.getYProperty());
		
//		vertical.startXProperty().bindBidirectional(src.translateXProperty());
//		vertical.startYProperty().bindBidirectional(src.translateYProperty());
//		vertical.endXProperty().bindBidirectional(src.translateXProperty());
//		vertical.endYProperty().bindBidirectional(dst.translateYProperty());
//		
//		horizontal.startXProperty().bindBidirectional(src.translateXProperty());
//		horizontal.startYProperty().bindBidirectional(dst.translateYProperty());
//		horizontal.endXProperty().bindBidirectional(dst.translateXProperty());
//		horizontal.endYProperty().bindBidirectional(dst.translateYProperty());
		
//		vertical.startXProperty().bind(src.getXProperty());
//		vertical.startYProperty().bind(src.getYProperty());
//		vertical.endXProperty().bind(src.getXProperty());
//		vertical.endYProperty().bind(dst.getYProperty());
//		
//		horizontal.startXProperty().bind(src.getXProperty());
//		horizontal.startYProperty().bind(dst.getYProperty());
//		horizontal.endXProperty().bind(dst.getXProperty());
//		horizontal.endYProperty().bind(dst.getYProperty());
		
//		vertical.setStartX(src.getTranslateX());
//		vertical.setStartY(src.getTranslateY());
//		vertical.setEndX(src.getTranslateX());
//		vertical.setEndY(dst.getTranslateY());
//		
//		horizontal.setStartX(src.getTranslateX());
//		horizontal.setStartY(dst.getTranslateY());
//		horizontal.setEndX(dst.getTranslateX());
//		horizontal.setEndY(dst.getTranslateY());
		
		this.getChildren().add(vertical);
		this.getChildren().add(horizontal);
		
//		MoveTo moveTo = new MoveTo();

		// Set start coordinates of the path.
//		this.setTranslateX(src.getX());
//		this.setTranslateY(src.getX());
		
//		this.translateXProperty().bind(src.getXProperty());
//		this.translateYProperty().bind(src.getYProperty());

		// Set the destination of the path.
//		moveTo.setX(dst.getX());
//		moveTo.setY(dst.getY());

		
//		moveTo.xProperty().bindBidirectional(dst.getXProperty());
//		moveTo.yProperty().bindBidirectional(dst.getYProperty());
//		this.getElements().add(moveTo);
//
//		// Add a horizontal line from the current position to the x-coordinate
//		// of the destination.
//		HLineTo horizontal = new HLineTo();
//		this.getElements().add(horizontal);
//
//		// Add a vertical line from the current position to the y-coordinate of
//		// the destination.
//		VLineTo vertical = new VLineTo();
//		this.getElements().add(vertical);
		
//		horizontal.xProperty().bindBidirectional(dst.translateXProperty());
//		vertical.yProperty().bindBidirectional(dst.translateXProperty());
	}
	
}
