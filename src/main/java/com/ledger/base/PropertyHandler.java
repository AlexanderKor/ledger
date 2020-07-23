package com.ledger.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {

	private static PropertyHandler instance = null;
	private static Properties props;

	private PropertyHandler() {
	}

	private static PropertyHandler getInstance() {
		if (instance == null)
			instance = new com.ledger.base.PropertyHandler();
		props = new Properties();
		props = openPropertyFile(props, "config.properties");
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}

	public static Properties openPropertyFile(Properties props, String fileName) {
		try {
			InputStream propertiesStream = new FileInputStream("src/main/resources/" + fileName);
			props.load(propertiesStream);
			propertiesStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
