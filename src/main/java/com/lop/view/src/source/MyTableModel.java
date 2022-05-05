package com.lop.view.src.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	
	List<String> columns = new ArrayList<>();//Nom des titres de columns
	List<Object []> data = new ArrayList<>();
	
	public String[] getColumnNames() {
		String [] col = null;
		if(columns.isEmpty()) col = new String [] {};
		else col = columns.toArray(new String[0]);
		return col;
		
	}

	@Override
	public int getRowCount() {
		return data.size();
		
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	
	public String getColumnName(int columnIndex) {
		return columns.get(columnIndex);
	}
	
	public void setColumnIdentifiers(String [] column) {
		if (!Arrays.equals(getColumnNames(), column)) {
			this.data = new ArrayList<>();
		}
		this.columns = Arrays.asList(column);
		this.fireTableStructureChanged();
	} 
	
	public void addRow(Object [] row) {
		data.add(row);
		this.fireTableRowsInserted(getRowCount(), getColumnCount());
	}

}
