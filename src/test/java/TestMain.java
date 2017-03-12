import com.intrack.executor.resultset.DefaultResultSetHandler;
import com.intrack.executor.resultset.ResultSetHandler;
import com.intrack.executor.resultset.ResultSetWrapper;
import com.intrack.type.JdbcType;
import com.intrack.type.TypeHandler;
import com.intrack.type.TypeHandlerRegistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        Statement statement = (Statement) connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id > ?");

        TypeHandler<Integer> integerTypeHandler = (TypeHandler<Integer>) typeHandlerRegistry.getTypeHandler(Integer.class);

        integerTypeHandler.setParameter(preparedStatement, 1, Integer.valueOf(1), null);
        preparedStatement.executeQuery();
        //ResultSet resultSet = statement.executeQuery("select * from users");

//        ResultSet resultSet = preparedStatement.getResultSet();
//
//        /* Get resultSet metadata */
//        ResultSetMetaData metaData = resultSet.getMetaData();
//        List<TypeHandler<?>> typeHandlers = new ArrayList<>();
//        int column = metaData.getColumnCount();
//
//        for (int i = 1; i <= column; i++) {
//            JdbcType jdbcType = JdbcType.forCode(metaData.getColumnType(i));
//            typeHandlers.add(typeHandlerRegistry.getTypeHandler(jdbcType));
//        }
//        while (resultSet.next()) {
//            //System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
//
//            for (int i = 1; i <= typeHandlers.size(); i++) {
//                TypeHandler typeHandler = typeHandlers.get(i - 1);
//                System.out.println(typeHandler.getResult(resultSet, i));
//            }
//            System.out.println("---------------------");
//        }

        ResultSetWrapper resultSetWrapper = new ResultSetWrapper(preparedStatement);
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(resultSetWrapper);

        resultSetHandler.handlerResultSets(preparedStatement);
    }
}
