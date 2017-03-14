package com.intrack.executor.resultset;

import com.intrack.executor.ExecutorException;
import com.intrack.type.JdbcType;
import com.intrack.type.TypeHandler;
import com.intrack.type.TypeHandlerRegistry;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author intrack
 */
public class ResultSetWrapper {

    private ResultSet resultSet = null;
    private List<TypeHandler<?>> typeHandlers = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();

    public ResultSetWrapper(Statement statement) {
        try {
            resultSet = statement.getResultSet();

            /* Get resultSet metadata */
            ResultSetMetaData metaData = resultSet.getMetaData();
            int column = metaData.getColumnCount();

            for (int i = 1; i <= column; i++) {
                JdbcType jdbcType = JdbcType.forCode(metaData.getColumnType(i));
                typeHandlers.add(TypeHandlerRegistry.getTypeHandler(jdbcType));

                columnNames.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            throw new ExecutorException("get result set error");
        }
    }

    public boolean next() throws SQLException {
        return resultSet.next();
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public List<TypeHandler<?>> getTypeHandlers() {
        return this.typeHandlers;
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public void close() throws SQLException {
        resultSet.close();
    }

}
