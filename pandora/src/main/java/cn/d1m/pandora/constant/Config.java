package cn.d1m.pandora.constant;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	
	protected static final Properties bootstrap;
	
	static {
		bootstrap = getProperties("bootstrap.properties");
	}

	public static Properties getProperties(String fileName){
        
        InputStreamReader is = null;
        Properties properties=new Properties();
		try {
			is = new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8);
       
            properties.load(is);
           
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
        	 if(is!=null){
                 try {
					is.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
             }
        }
        
        return properties;
    }
}
