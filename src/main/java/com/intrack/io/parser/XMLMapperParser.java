package com.intrack.io.parser;

import com.intrack.session.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author intrack
 */
public class XMLMapperParser {

    private Configuration configuration;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(String filename) throws FileNotFoundException {
        parse(new FileInputStream(filename));
    }

    public void parse(InputStream inputStream) {

    }

}
