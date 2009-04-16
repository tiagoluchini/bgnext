package org.luchini.bgserver.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {

	public static Logger getLogger(Class<?> clazz) {
		PropertyConfigurator.configure("log4j.properties");
		return Logger.getLogger(clazz);
	}
	
}
