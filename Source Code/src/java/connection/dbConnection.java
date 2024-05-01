/**
 * @author Janmejay Kr Singh
 * All Rights Reserved.
 */
package connection;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/cryptography";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    
    static Connection con = null;
    PreparedStatement pst = null;

    public static Connection makeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            // Print error details if connection fails
            e.printStackTrace();
        }
        return con;
    }
}