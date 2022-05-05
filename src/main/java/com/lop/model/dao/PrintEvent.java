package com.lop.model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PrintEvent {
    private final Factory factory;

    public PrintEvent(Factory factory) {
        this.factory = factory;
    }

    public List<List<String>> print(String query) throws SQLException {
        Statement statement = this.factory.getConnection().createStatement();
        ResultSet answer = statement.executeQuery(query);
        List<List<String>> data = new ArrayList<>();

        /*I use this ResultSet to have some information like ColumnName and ColumnCount*/
        ResultSetMetaData metadata = answer.getMetaData();
        int numberOfColumn = metadata.getColumnCount();
        List<String> header = new ArrayList<>();

        /*Header add*/
        for (int i = 1; i < numberOfColumn + 1; i++) {
            header.add(metadata.getColumnName(i));
        }
        data.add(header);

        /*Row add*/
        while (answer.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i < numberOfColumn + 1; i++) {
                row.add( answer.getNString(i));
            }
            data.add(row);
        }
        return data;
    }
}
