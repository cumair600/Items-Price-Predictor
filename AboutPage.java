package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class AboutPage {

	@FXML JFXButton btnBack;
	@FXML AnchorPane aboutPane; // About Page Panel
	
	public void clickBack(ActionEvent event) throws IOException
	{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("StartPage.fxml")); // Loading the Start Page
		aboutPane.getChildren().setAll(pane);
	}
}
