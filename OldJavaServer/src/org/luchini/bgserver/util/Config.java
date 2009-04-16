package org.luchini.bgserver.util;

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
	
	public static final String CONFIG_FILE = "bgserver.properties";
	
	public static final String PORT = "bgserver.server.port";
	public static final String LISTCONTENDER_TIMEOUT = "bgserver.server.listcontender.timeout";
	
	public static final String SERVER_PROTOCOL_QTY = "bgserver.server.protocol.qty";
	public static final String SERVER_PROTOCOL_CLASS_PREFIX = "bgserver.server.protocol.class.";
	
	public static final String GAMES_QTY = "bgserver.games.qty";
	public static final String GAMES_ENGINE_CLASS_PREFIX = "bgserver.games.engine.class.";
	
	private Config() {
		this.seqs = new TreeMap<String, Byte>();
		try {
			FileInputStream inStream = new FileInputStream(CONFIG_FILE);
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
	
	public byte nextSequential(String key) {
		if (this.seqs.get(key) == null)
			this.seqs.put(key, new Byte((byte)0));
		byte b = this.seqs.get(key).byteValue();
		if (b == Byte.MAX_VALUE)
			b = 0;
		b++;
		this.seqs.put(key, new Byte(b));
		return b;
	}
	
	public static String uniqueID() {
		return UUID.randomUUID().toString();
	}
	
}
