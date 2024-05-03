package edu.icet.demo.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class LoadDBDriver {
    private static LoadDBDriver instance;
    private final Connection connection;

    private LoadDBDriver() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_thoga_kade?autoReconnect=true&useSSL=false", "root", "200284");
    }

    public static LoadDBDriver getLoadDBDriverInstance() throws SQLException {
        if(instance==null){
            instance = new LoadDBDriver();
        }
        return instance;
    }
}
