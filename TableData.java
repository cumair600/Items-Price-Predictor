package application;

import javafx.beans.property.SimpleStringProperty;

public class TableData {
	SimpleStringProperty month; // Column 1
	SimpleStringProperty price; // Column 2
	
	public TableData(String m, String p)
	{
		month = new SimpleStringProperty(m);
		price = new SimpleStringProperty(p);
	}
	
	public void setMonth(SimpleStringProperty m)
	{
		this.month = m;
	}
	
	public void setPrice(SimpleStringProperty p)
	{
		this.price = p;
	}
	
	public String getMonth()
	{
		return month.get();
	}
	
	public String getPrice()
	{
		return price.get();
	}
}
