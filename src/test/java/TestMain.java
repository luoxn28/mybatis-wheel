import java.sql.*;

/**
 * for test
 * @author intrack
 */
public class TestMain {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://192.168.1.150/ssh_study";
        String username = "luoxn28";
        String password = "123456";
        Connection connection = (Connection) DriverManager.getConnection(url, username, password);

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
        }
    }
}
