package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import gui.toolbar.ImportGfa;
import gui.toolbar.RecentGfa;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Björn Ho
 */
public class MainController implements Initializable {
	/**
	 * You can interact with an individual tab page if you need it. (Uncomment
	 * if necessary).
	 */
	// @FXML private GridPane ribbonTab;
	// @FXML private TabPane tabPane;
	@FXML private VBox verticalBox;
	@FXML private Menu recentMenu;

	/**
	 * Right now this MainController is empty but perhaps there will be 
	 * additions later on. Keeping it for now.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MenuItem test = new MenuItem();
		test.setText("testing");
		test.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println("testing");
		    }
		});
		recentMenu.getItems().add(test);
		addRecentItems();
	}
	
	private void addRecentItems() {
		RecentGfa rGfa = new RecentGfa();
		HashMap<String, String> recentMap = rGfa.readRecent();
		for (String name : recentMap.keySet()) {
			MenuItem item = new MenuItem();
			item.setText(name);
			recentMenu.getItems().add(item);
		}
	}
	
	 public void importNew(final ActionEvent e) {
		 final FileChooser fileExplorer = new FileChooser();
		 fileExplorer.getExtensionFilters().addAll(new ExtensionFilter("gfa files", "*.gfa"));
		 File file = fileExplorer.showOpenDialog(verticalBox.getScene().getWindow());
		 if (file != null) {
			 ImportGfa importer = new ImportGfa(Launcher.stage, file.getAbsolutePath(), file.getName());
			 importer.startImport();
         }
	 }
}
