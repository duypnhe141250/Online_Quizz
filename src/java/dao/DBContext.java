/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Dell Inc
 */
public class DBContext {

//     private final String serverName = "localhost";
//    private final String dbName = "online_quizz";
//    private final String portNumber = "1433";
    public static String url;
    public static String user="sa";
    public static String pass="123";

    /*Change/update information of your database connection, DO NOT change name of instance variables in this class*/

    public Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://" + "localhost" + ":" + "1433" + ";databaseName=" + "online_quiz";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DBContext() {
    }

    public void closeConnection(Connection connection, PreparedStatement pre, ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (pre != null && !pre.isClosed()) {
            pre.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
