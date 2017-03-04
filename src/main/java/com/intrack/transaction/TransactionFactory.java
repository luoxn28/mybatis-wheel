package com.intrack.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author intrack
 */
public interface TransactionFactory {

    /**
     * 设置自定义属性
     */
    void setProperties(Properties props);

    /**
     * 获取Connection
     */
    Transaction newTransaction(Connection conn);

    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
