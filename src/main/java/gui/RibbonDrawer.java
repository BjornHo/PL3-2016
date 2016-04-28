package gui;

import gui.semanticzoom.NodeGestures;
import gui.semanticzoom.PannableCanvas;

import java.sql.Statement;
import java.util.ArrayList;

import coordinates.Coordinate;
import coordinates.CoordinateDetermination;
import db.DatabaseManager;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class RibbonDrawer {
	private DatabaseManager dbm;
	
	public RibbonDrawer (DatabaseManager db){
		this.dbm = db;
	}

	public Group draw(PannableCanvas canvas, NodeGestures nodeGestures) {
		Coordinate[] coords = CoordinateDetermination.calcCoords();
		ArrayList<Integer> from = dbm.getDBReader().getAllFromID();
		ArrayList<Integer> to = dbm.getDBReader().getAllToID();
		
		int maxX = getMaxX(coords);
		int maxY = getMaxY(coords);
		
		Group group = new Group();
		
		for(int i = 0; i < from.size(); i++) {
			int fromID = from.get(i);
			int toID = to.get(i);
			Path path = drawPath(coords[fromID-1], coords[toID-1], maxX, maxY);
	        path.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
	        path.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
	        path.setStrokeWidth(dbm.getDBReader().countGenomesInLink(fromID, toID));
	        canvas.getChildren().add(path);
		}
		return group;
	}
	
	private int getMaxX(Coordinate[] coordinates) {
		int x = 0;
		for(int i = 0; i < coordinates.length; i++) {
			if(coordinates[i].getX() > x) {
				x = coordinates[i].getX();
			}
		}
		return x;
	}
	
	private int getMaxY(Coordinate[] coordinates) {
		int y = 0;
		for(int i = 0; i < coordinates.length; i++) {
			if(coordinates[i].getY() > y) {
				y = coordinates[i].getY();
			}
		}
		return y;
	}
	
	private Path drawPath(Coordinate from, Coordinate to, int maxX, int maxY) {
		MoveTo moveto = new MoveTo(600/(maxX + 2) * from.getX() + 600/(maxX + 2), 600/(maxY + 2) * from.getY());
		LineTo lineto = new LineTo(600/(maxX + 2) * to.getX()+ 600/(maxX + 2), 600/(maxY + 2) * to.getY());
		Path path = new Path();
		path.getElements().addAll(moveto, lineto);
		return path;
	}
}
