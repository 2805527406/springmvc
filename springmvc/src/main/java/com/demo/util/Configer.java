package com.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configer {
	/*
	 * 閰嶇疆鏂囦欢璺緞
	 */
	private static final String cfgFile = "/config.properties";
	/**
	 * 璇诲嚭鐨勫睘鎬�
	 */
	private static Properties properties;

	private Configer() {

	}

	static {
		properties = new Properties();
		InputStream is = Configer.class.getResourceAsStream(cfgFile);
		try {
			properties.load(is);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("璇诲彇propertise灞炴�ф枃浠跺け璐ワ紝璇烽噸璇曪紒");
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 璇诲彇鏂囦欢灞炴��
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(String propertyName) {
		if (properties == null) {
			throw new RuntimeException("绯荤粺閿欒锛氳鍙朿onfig灞炴�ф枃浠跺け璐ワ紒");
		} else {
			return properties.getProperty(propertyName);
		}
	}
}
