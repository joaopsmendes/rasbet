package rasbetDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTest {
    Connection connection;


    public DBTest(Connection connection) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost/","root","root");
    }


    public void query() throws SQLException {
        String query = "INSERT INTO x ()VALUES ()";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.execute();
    }

}
