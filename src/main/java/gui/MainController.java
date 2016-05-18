package gui;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import db.DatabaseManager;
import org.apache.commons.io.FilenameUtils;
import toolbar.ExistingHandler;
import toolbar.RecentHandler;




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
	
	/**
	 * Access to the controllers to update the view when changing files.
	 */
	@FXML private GraphController graphTabController;
	@FXML private RibbonController ribbonTabController;
	
	/**
	 * Access to the scene window.
	 */
	@FXML private VBox verticalBox;
	
	/**
	 * Access to the menus of the toolbar, to add new sub menus.
	 */
	@FXML private Menu recentMenu;
	@FXML private Menu existingMenu;

	/**
	 * Adds the recent and existing items into the toolbar menus.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addItems(new RecentHandler().getRecent(), recentMenu);
		addItems(new ExistingHandler().buildExistingMap(), existingMenu);
	}
	
	/**
	 * Used to add recent items into the toolbar menus. When clicked, it will fire the 
	 * event handler to open the specified item.
	 * @param map	A hashmap which contains the file name as key and the directory as value.
	 * @param menu	The menu in which these items get added into.
	 */
	private void addItems(HashMap<String, String> map, Menu menu) {
		for (String name : map.keySet()) {
			MenuItem item = new MenuItem();
			item.setText(name);
			item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent actionEvent) {
			        openExisting(map.get(name), name);
			    }
			});
			menu.getItems().add(item);
		}
	}
	
	/**
	 * Used to import a new .gfa file. It will open up a file explorer to browse to your file.
	 * It will build the recently opened file submenu and the existing submenu accordingly.
	 * @param actionEvent	An Event representing some type of action
	 */
	 public void importNew(final ActionEvent actionEvent) {
		 final FileChooser fileExplorer = new FileChooser();
		 fileExplorer.getExtensionFilters().addAll(new ExtensionFilter("gfa files", "*.gfa"));
		 File file = fileExplorer.showOpenDialog(verticalBox.getScene().getWindow());
		 if (file != null) {
			 RecentHandler recent = new RecentHandler();
			 String fileName = FilenameUtils.removeExtension(file.getName());
				final String dbPath = System.getProperty("user.dir") 
						+ File.separator + "db" + File.separator + fileName;
			 recent.buildRecent(dbPath, fileName);
			 ImportHandler importer = new ImportHandler(
					 Launcher.stage, file.getAbsolutePath(), fileName);
			 importer.startImport();
			 updateExisting();
         }
	 }
	 
	 /**
	  * Used to open an existing database. It will first close the existing database
	  * connection. After that it will update the recently opened menu order.
	  * Finally it will update the views to display the graphs correctly.
	  * @param dbPath
	  * @param name
	  */
	 private void openExisting(String dbPath, String name) {
		Launcher.dbm.closeDbConnection();
		Launcher.dbm = new DatabaseManager(dbPath);
		updateRecent(dbPath, name);
		ribbonTabController.updateView();
		graphTabController.updateView();
	 }
	 
	 /**
	  * Used to update the recent menu in the toolbar. It will first build it and 
	  * then add the result to the menu on the toolbar.
	  * @param dbPath		path to these database
	  * @param name			name of the file
	  */
	 private void updateRecent(String dbPath, String name) {
			RecentHandler recentHandler = new RecentHandler();
			recentHandler.buildRecent(dbPath, name);
			recentMenu.getItems().clear();
			addItems(recentHandler.getRecent(), recentMenu);
	 }
	 
	 /**
	  * Used to update the existing menu in the toolbar. It will first build it and 
	  * then add the result to the menu on the toolbar.
	  */
	 private void updateExisting() {
			ExistingHandler existHandler = new ExistingHandler();
			existingMenu.getItems().clear();
			addItems(existHandler.buildExistingMap(), existingMenu);
	 }
	 
	 /**
	  * This executes whenever quit has been activated from
	  * the toolbar. It quits the program.
	  * @param actionEvent	An Event representing some type of action
	  */
	 public void Quit(final ActionEvent actionEvent) {
		 System.exit(0);
	 }
}