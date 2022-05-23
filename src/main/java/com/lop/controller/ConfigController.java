package com.lop.model.dao;

import java.sql.ResultSet;

public class BaseExist {
    public static boolean baseExist() {
        try {
            String dbName = "manager";
            ResultSet rs = getConnection().getMetaData().getCatalogs();

            while (rs.next()) {
                String catalogs = rs.getString(1);
                if (dbName.equals(catalogs)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;

    }
}
