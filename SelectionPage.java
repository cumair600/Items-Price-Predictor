package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SelectionPage implements Initializable {
	
	
	@FXML JFXButton btnHelp;
	@FXML AnchorPane selectionPane;
	@FXML JFXSpinner spinner;
	@FXML JFXComboBox<String> comboItem;
	@FXML JFXComboBox<String> comboCity;
	@FXML JFXComboBox<String> comboMonths;
	@FXML JFXButton btnBack;
	
	HashMap<String, Integer> models = new HashMap<>();
	
	public void clickBack(ActionEvent event) throws IOException
	{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("StartPage.fxml")); // Loading the Start Page
		selectionPane.getChildren().setAll(pane);
		
	}
	
	@FXML
	public void clickHelp(ActionEvent event) throws IOException
	{
		Alert dg = new Alert(Alert.AlertType.INFORMATION); // Alert Dialog Box pop up
		dg.setTitle("Help");
		dg.setHeaderText("User Manual");
		dg.setContentText("- Select an Item for which you want to forecast the future prices from the items list.\n\n- Select a City for which you want to forecast the future prices from the cities list.\n\n- Select a period of time for which you want to forecast the future prices from the Months list.");
		dg.setResizable(true);
		dg.getDialogPane().setPrefSize(580, 320);
		dg.show();
	}
	
	@FXML
	public void clickForecast(ActionEvent event) throws IOException
	{
		if(comboItem.getValue() == null || comboCity.getValue() == null || comboMonths.getValue() == null) // When no Parameter is selected
		{
			Notifications notification = Notifications.create().title("Error").text("You have to select an item, city and months period for forecasting!").graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_CENTER);
			notification.showConfirm(); // Error Message saying you have to select the options first.
		}
		else // if all the options are selected
		{
			String item = comboItem.getValue(); // Getting Values from the Combo Boxes
			String city = comboCity.getValue();
			String months = comboMonths.getValue();
			if(forecast(item, city, months)) // Calling the forecast method
			{
				AnchorPane pane = FXMLLoader.load(getClass().getResource("ForecastPage.fxml")); // Loading the Forecast Page
				selectionPane.getChildren().setAll(pane);
			}
		}
	}
	
	public boolean forecast(String item, String city, String months) // This method starts the python process to forecast prices
	{	
		item = item.replaceAll("\\s", "-");
		String file = item + "_" + city + "_model" +  ".pickle";
		if(models.containsKey(file)) // checking to see whether the model exists or not
		{
			// CMD Command to run python Script of forecasting
			String dir =  "cd \"C:\\Users\\Umair Afzal\\eclipse-workspace\\FYP\\src\\application\" && python predict.py " + item + " " + city + " " + months; 
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", dir);
			builder.redirectErrorStream(true);
			Process p = null;
			try {
				p = builder.start();
				try {
					p.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}	
		else //  if the desired model is not available
		{
			Notifications notification = Notifications.create().title("Error").text("No Model Found! You have to train a model for that item first.").graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_CENTER);
			notification.showConfirm(); // Error message saying that you need to train model for that item first.
			return false;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		spinner.setVisible(false);
		//Initializing the combo boxes
		ObservableList<String> items = FXCollections.observableArrayList("BANANAS", "BATH SOAP LIFEBOUY", "BEEF", "BREAD PLAIN MEDIUM", "CHAPPAL SPONGE BATA", "CHICKEN", "CIGARETTES K-2", "COOKED BEEF PLATE", "COOKED DAL PLATE", "COOKING OIL", "CURD", "DIESEL", "EGG HEN", "ELECTRIC CHARGES", "ENERGY SAVOR 14W", "FIREWOOD", "GARLIC", "GAS CHARGES", "GEORGETTE", "GRAM PULSE", "GUR", "KEROSENE", "LAWN", "LONG CLOTH", "LPG 11KG CYLINDER", "MASH PULSE", "MASOOR PULSE", "MATCH BOX", "MILK FRESH", "MILK NIDO", "MOONG PULSE", "MUSTARD OIL", "MUTTON", "ONIONS", "PETROL", "POTATOES", "RED CHILLIES POWDER", "RICE BASMATI", "RICE IRRI-6", "SALT POWDER", "SANDAL GENTS BATA", "SANDAL LADIES BATA", "SHIRTING", "SUGAR", "TEA PREPARED SADA", "TEA YELLOW LABEL", "TELEPHONE LOCAL CALL", "TOMATOES", "VEGETABLE GHEE LOOSE", "VEGETABLE GHEE TIN", "WASHING SOAP", "WHEAT", "WHEAT FLOUR");
		ObservableList<String> cities = FXCollections.observableArrayList("ISLAMABAD","RAWALPINDI","GUJRANWALA","SIALKOT","LAHORE","FAISALABAD","SARGODHA","MULTAN","BAHAWALPUR","KARACHI","HYDERABAD","SUKKUR","LARKANA","PESHAWAR","BANNU","QUETTA","KHUZDAR");
		ObservableList<String> months = FXCollections.observableArrayList("3-Months", "6-Months", "9-Months", "1-Year", "2-Years", "3-Years");
		comboItem.setItems(items);
		comboCity.setItems(cities);
		comboMonths.setItems(months);
		
		// Getting the trained models information
		final File folder = new File("C:\\Users\\Umair Afzal\\eclipse-workspace\\FYP\\src\\application\\Models");
		listFilesForFolder(folder);
	}
	
	public void listFilesForFolder(final File folder) { // Helper Function to get names of the models
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            models.put(fileEntry.getName(), 1); // Readying up a hash map for faster retrieval
	        }
	    }
	}


}
