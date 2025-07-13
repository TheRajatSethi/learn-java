package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Connection connection = null;

    private static void initialize(){
        String url = "jdbc:sqlite:database.db";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        if (connection == null){
            initialize();
        }
        return Database.connection;
    }

    public static Map<String, Object> convertToObjectSingle(ResultSet rs) throws SQLException {
        Map<String, Object> result = new HashMap<>();

        for (int i=1; i<=rs.getMetaData().getColumnCount(); i++ ){
            result.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
        }
        return result;
    }
}
