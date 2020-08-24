/*
* Description: 
* This class is used to manage connectiond with the database.
*/

// Required imports
import java.sql.*;

public class dbConnection {

    // Class members
    static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/asshahan";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection getConnection() {
    try {
    	Class.forName("org.mariadb.jdbc.Driver");
    	String user = "asshahan";
    	String passwd = "200311941";
    	conn = DriverManager.getConnection(jdbcURL, user, passwd);
    	stmt = conn.createStatement();
    	return conn;
    }
    catch(Exception e) {
    	System.out.println(e);
    }
    return null;
    }
}
    

	
	
	

       