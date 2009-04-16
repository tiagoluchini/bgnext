package org.luchini.rgalaxy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;

public class Config {

	private static Config myInstance;
	
	private Properties props;
	private Map<String, Byte> seqs;

	public static final String CONFIG_FILE = "rgalaxy.properties"; 
	public static final String MAX_SEATS = "rgalaxy.max.seats";
	
	private Config() {
		this.seqs = new TreeMap<String, Byte>();
		try {
			FileInputStream inStream = new FileInputStream(Config.CONFIG_FILE);
			this.props = new Properties();
			this.props.load(inStream);
			inStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static Config getInstance() {
		if (myInstance == null)
			myInstance = new Config();
		return myInstance;
	}
	
	public String getEntry(String key) {
		return this.props.getProperty(key);
	}
		
}
