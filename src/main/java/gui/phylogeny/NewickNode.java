package gui.phylogeny;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Class that is used to visualize a single node of a {@link NewickTree}
 */
public class NewickNode extends Group {
	
	/**
	 * The rectangle shape that represents a NewickNode in the phylogenetic tree.
	 */
	private Rectangle node;
	
	/**
	 * Size of the rectangle representing the node.
	 */
	private static final int RECTANGLE_SIZE = 10;
	
	/**
	 * Text label displaying the genome ID of leaf nodes.
	 */
	private Label label = null;
	
	/**
	 * Lineage of the specimen.
	 */
	private String lineage = "";
	
	/**
	 * variable for labeling a leaf node.
	 */
	private boolean isLeaf = false;
	
	/**
	 * variable for labeling a node as selected.
	 */
	private boolean isSelected = false;
	
	/**
	 * Main constructor of NewickNode.
	 */
	public NewickNode() {
		this.setupNodeLayout();
		this.getChildren().add(node);
	}
	
	/**
	 * Leafnode constructor that adds a label with the given name to a leaf node and colours it
	 * accordingly.
	 * 
	 * @param name
	 *            Text that the Label should display.
	 * @param lineage
	 * 			  Lineage of the specimen.
	 */
	public NewickNode(String name, String lineage) {
		this();
		this.lineage = lineage;
		this.setColoured();
		this.addLabel(name);
	}
	
	/**
	 * Handles node layout aspects.
	 */
	public void setupNodeLayout() {
		node = new Rectangle(0 - RECTANGLE_SIZE / 2,
				0 - RECTANGLE_SIZE / 2, RECTANGLE_SIZE, RECTANGLE_SIZE);
		node.addEventFilter(MouseEvent.MOUSE_CLICKED, new NewickNodeMouseEventHandler(this));
	}
	
	/**
	 * Returns the nodes rectangle object so that it can be altered.
	 * 
	 * @return node
	 */
	public Rectangle getRectangle() {
		return node;
	}
	
	/**
	 * Sets the nodes rectangle to visibility false, making it invisible.
	 */
	public void hideRectangle() {
		node.setVisible(false);
	}
	
	/**
	 * Adds a label with the given text to the Group of the node.
	 * 
	 * @param text
	 * 			Text the label will display
	 */
	private void addLabel(String text) {
		label = new Label(text);
		setupLabelLayout();
		this.getChildren().add(label);
	}
	
	/**
	 * Handles label layout aspects.
	 */
	private void setupLabelLayout() {
		label.setTranslateX(10);
		label.setTranslateY(-8);
		label.addEventFilter(MouseEvent.MOUSE_CLICKED, new NewickNodeMouseEventHandler(this));
		label.setTextFill(this.getColour());
	}
	
	/**
	 * Returns the nodes Label object so that it can be altered.
	 * @return
	 */
	public Label getLabel() {
		return label;
	}
	
	public String getLineage() {
		return this.lineage;
	}

	/**
	 * Toggles "selected" state of a node.
	 */
	protected void toggleSelected() {
		if (isSelected) {
			isSelected = false;
		} else {
			isSelected = true;
		}
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	/**
	 * Sets the node's isLeaf value to the given value.
	 * 
	 * @param value
	 * 			true or false.
	 */
	public void setIsLeaf(boolean value) {
		isLeaf = value;
	}
	
	/**
	 * Returns whether the node is a leaf node or not.
	 * 
	 * @return isLeaf
	 * 			true or false.
	 */
	public boolean isLeaf() {
		return isLeaf;
	}
	
	/**
	 * Get colour corresponding to this node's lineage.
	 * 
	 * @return Paint
	 */
	public Paint getColour() {
		return NewickColourMatching.getLineageColour(this.getLineage());
	}
	
	/**
	 * Set colour corresponding to node's lineage.
	 */
	public void setColoured() {
		node.setFill(this.getColour());
		if (this.isLeaf()) {
			label.setTextFill(this.getColour());
		} else {
			for (Object child : this.getChildren()) {
				if (child instanceof NewickNode) {
					((NewickNode) child).setColoured();
				}
			}
		}
	}
	
	/**
	 * Unset colour of this node (makes node appear gray).
	 */
	public void unsetColoured() {
		node.setFill(NewickColourMatching.getDeactivatedColour());
		if (this.isLeaf()) {
			label.setTextFill(NewickColourMatching.getDeactivatedColour());
		} else {
			for (Object child : this.getChildren()) {
				if (child instanceof NewickNode) {
					((NewickNode) child).unsetColoured();
				}
			}
		}
	}
}