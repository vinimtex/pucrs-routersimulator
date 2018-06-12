package models;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RouterTable {
	
	private CopyOnWriteArrayList<RouterTableRow> rows;
	
	public RouterTable() { this.rows = new CopyOnWriteArrayList<>();}
	
	public void addRow(RouterTableRow row) {
		this.rows.add(row);
	}
	
	public void removeRow(RouterTableRow row) {
		this.rows.remove(row);
	}
	
	public void removeRowByIndex(int index) {
		this.rows.remove(index);
	}

	public CopyOnWriteArrayList<RouterTableRow> getRows() {
		rows.stream().filter(row -> row.isSoftDeleted() == false).collect(Collectors.toList());
		return rows;
	}

}
