package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ForecastPage implements Initializable{
	
	ArrayList<Double> predictions;
	ArrayList<String> list;
	ArrayList<String> months;
	ArrayList<String> data;
	
	@FXML AnchorPane forecastPane;
	@FXML JFXButton btnBack;
	@FXML LineChart<String, Double> chartPrice;
	@FXML CategoryAxis x;
	@FXML NumberAxis y;
	@FXML TableView<TableData> tablePrice;
	@FXML TableColumn<TableData,String> colMonth;
	@FXML TableColumn<TableData,String> colPrice;
	@FXML Label txtItem, txtCity;
	
	void loadData() // To Load the data on Screen in the Initializing process 
	{
		Scanner s1 = null;
		try {
			// Loading the Meta-data of the items for which forecasting is being done.
			s1 = new Scanner(new File("C:\\Users\\Umair Afzal\\eclipse-workspace\\FYP\\src\\application\\Data\\data.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// data contains item name and city name
		data = new ArrayList<String>();
		while (s1.hasNext()){
		    data.add(s1.next());
		}
		s1.close(); // closing scanner 1
		
		Scanner s2 = null;
		try {
			// Retrieving the model's predictions into the java code
			s2 = new Scanner(new File("C:\\Users\\Umair Afzal\\eclipse-workspace\\FYP\\src\\application\\Predictions\\" + data.get(0) + "_" + data.get(1) + "_" + "pred.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Adding the prediction into a String list
		list = new ArrayList<String>();
		while (s2.hasNext()){
		    list.add(s2.next());
		}
		s2.close(); //closing scanner 2
		
		// Converting the String Predictions into Double values
		predictions = new ArrayList<Double>();
		for(String st : list)
		{
			predictions.add(Double.parseDouble(st));
		}
		
	}
	
	public ObservableList<TableData> getTableData() // Getting the data for the Table to setup on the GUI
	{
		ObservableList<TableData> entries = FXCollections.observableArrayList(); // A list for the entries in the table
		for(int i = 0; i < list.size(); i++)
		{
			entries.add(new TableData(months.get(i), list.get(i)));
		}
		return entries;
	}
	
	public XYChart.Series getChartData() // Getting the data for the Chart to setup on the GUI
	{
		XYChart.Series series = new XYChart.Series(); // A list for the entries in the chart
		for(int i = 0; i < predictions.size(); i++) 
		{
			series.getData().add(new XYChart.Data(months.get(i), predictions.get(i)));
		}
		series.setName("Items Price per Month");
		return series;
		
	}
	
	public void clickBack(ActionEvent event) throws IOException
	{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("SelectionPage.fxml")); // Loading The Selection Page
		forecastPane.getChildren().setAll(pane);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // Initializer
		
		loadData(); // Loading the data to display on table and chart
		txtItem.setText("Item : " + data.get(0)); // Setting the Labels on the Screen for the item and city
		txtCity.setText("City : " + data.get(1));
		
		// Array of Months 
		months = new ArrayList<String>(Arrays.asList("2020-04","2020-05","2020-06","2020-07","2020-08","2020-09","2020-10","2020-11","2020-12","2021-01","2021-02","2021-03","2021-04","2021-05","2021-06","2021-07","2021-08","2021-09","2021-10","2021-11","2021-12","2022-01","2022-02","2022-03","2022-04","2022-05","2022-06","2022-07","2022-08","2022-09","2022-10","2022-11","2022-12","2023-01","2023-02","2023-03","2023-04","2023-05","2023-06","2023-07","2023-08","2023-09","2023-10","2023-11","2023-12"));
	
		// Setting the Table Columns 
		colMonth.setCellValueFactory(new PropertyValueFactory<TableData,String>("Month"));
		colPrice.setCellValueFactory(new PropertyValueFactory<TableData,String>("Price"));
		tablePrice.setItems(getTableData());
		
		// Setting the Chart Values on X and Y axis
		x.setLabel("Months");
		y.setLabel("Price");
		chartPrice.getData().addAll(getChartData());
	}
	

}
