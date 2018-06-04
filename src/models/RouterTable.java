package models;

import java.util.*;

public class RouterTable {
	
	private List<RouterTableRow> rows;
	
	public RouterTable() { this.rows = new ArrayList<>();}
	
	public void addRow(RouterTableRow row) {
		this.rows.add(row);
	}
	
	public void removeRow(RouterTableRow row) {
		this.rows.remove(row);
	}
	
	public void removeRowByIndex(int index) {
		this.rows.remove(index);
	}
	

}
