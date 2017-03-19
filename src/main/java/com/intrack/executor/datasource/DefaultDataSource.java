package com.intrack.executor.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author intrack
 */
public class DefaultDataSource extends BasicDataSource {

    {
        /* 设置DataSource */
        setDriverClassName("com.mysql.jdbc.Driver");
        setUrl("jdbc:mysql://192.168.1.150/ssh_study");
        setUsername("luoxn28");
        setPassword("123456");
    }

}
