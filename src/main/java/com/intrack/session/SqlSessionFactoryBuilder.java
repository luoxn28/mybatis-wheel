package com.intrack.session;

import com.intrack.io.XMLConfigBuilder;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @author intrack
 */
public class SqlSessionFactoryBuilder {

    /**
     * Build session factory according to configuration file
     */
    public SqlSessionFactory build(InputStream stream) {
        return build(stream, null, null);
    }

    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        XMLConfigBuilder configBuilder = new XMLConfigBuilder(inputStream, environment, properties);

        return build(configBuilder.parse());
    }

    public SqlSessionFactory build(Reader reader) {
        return build(reader, null, null);
    }

    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        return build(new Configuration());
    }

    public SqlSessionFactory build(Configuration configuration) {
        return null;
    }

}
