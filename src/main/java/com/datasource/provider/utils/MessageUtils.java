package com.datasource.provider.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 错误消息工具类
 * @author
 */
public class MessageUtils {

	private static Map<String,String> propertiesMap = new HashMap<>();
	  
	public MessageUtils(String propertyNames) {
		String[] names = propertyNames.split(",");
		for(String name : names) {
			loadAllProperties(name);
		}
    } 
  
    /**
     * 根据键Properties取得对应的值
     * @param key
     * @return
     */
    public static String getString(String key) { 
        return propertiesMap.get(key);  
    }
    

	private void processProperties(Properties props) throws BeansException { 
		for (Object key : props.keySet()) { 
			String keyStr = key.toString(); 
			propertiesMap.put(keyStr, props.getProperty(keyStr)); 
		}
	}
	
	private void loadAllProperties(String propertyName) { 
		try { 
			Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(propertyName));
			processProperties(properties); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}
}