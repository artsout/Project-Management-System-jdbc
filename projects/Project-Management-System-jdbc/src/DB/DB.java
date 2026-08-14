package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn=null;

    public  static Connection getConnection()  {
        if(conn==null){
            try {
            Properties props = loadProperties();
            String url= props.getProperty("dburl");

                conn = DriverManager.getConnection(url,props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }
    public  static  void closeConnection(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties p= new Properties();
            p.load(fs);
            return  p;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
    public static void closeStatement(Statement ps){
        try {
            if(ps!=null){
                ps.close();
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    public static void closeResult(ResultSet re){
        try {
            if(re!=null){
                re.close();
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    public  static void RollBack(Connection conn){
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DbException("Error trying to  rolled back! Caused by: "+ e.getMessage());
        }
    }
}
