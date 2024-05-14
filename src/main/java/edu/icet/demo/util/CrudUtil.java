package edu.icet.demo.util;

import edu.icet.demo.db.LoadDBDriver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... object) throws SQLException {
        if(sql.startsWith("INSERT")){
            PreparedStatement preparedStatement = LoadDBDriver.getLoadDBDriverInstance().getConnection().prepareStatement(sql);

            for(int i=0; i<object.length; i++){
                preparedStatement.setObject((i+1), object[i]);
            }
            int res = preparedStatement.executeUpdate();
            return (T) (Boolean) (res>0 ? true:false);
        }





        return null;
    }
}
