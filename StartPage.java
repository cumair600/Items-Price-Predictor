package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class StartPage {
	
	@FXML JFXButton btnAbout;
	@FXML JFXButton btnProceed;
	@FXML AnchorPane startPane; //Start Page Panel
	
	public void clickAbout(ActionEvent event) throws IOException
	{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("AboutPage.fxml")); // Loading the ABOUT Page
		startPane.getChildren().setAll(pane);
	}

	public void clickProceed(ActionEvent event) throws IOException
	{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("SelectionPage.fxml")); // Loading the Selection Page
		startPane.getChildren().setAll(pane);
	}
}
