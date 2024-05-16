package edu.icet.demo.util;

import edu.icet.demo.db.LoadDBDriver;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudUtil {
    public static <T> T execute(String sql, Object... object) throws SQLException {
        if(sql.startsWith("INSERT") || sql.startsWith("UPDATE") || sql.startsWith("DELETE")){
            PreparedStatement preparedStatement = LoadDBDriver.getLoadDBDriverInstance().getConnection().prepareStatement(sql);
            for(int i=0; i<object.length; i++){
                if(sql.startsWith("INSERT")){
                    preparedStatement.setObject((i+1), object[i]);
                } else if(sql.startsWith("UPDATE")) {
                    if (object.length - i != 1) {
                        preparedStatement.setObject((i + 1), object[i + 1]);
                    } else {
                        preparedStatement.setObject((i + 1), object[0]);
                    }
                } else {
                    preparedStatement.setObject((i+1), object[i]);
                }
            }
            int res = preparedStatement.executeUpdate();
            return (T) (Boolean) (res>0 ? true:false);

        } else if(sql.startsWith("SELECT")){
            Statement statement = LoadDBDriver.getLoadDBDriverInstance().getConnection().createStatement();
            return (T) statement.executeQuery(sql);
        }


        return null;
    }
}
