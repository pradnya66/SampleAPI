package base;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	
	public static final Logger logger = LogManager.getLogger(BaseClass.class.getName());


	public String randomestring()
	{
		String generateinvalidID=RandomStringUtils.randomNumeric(4);
		return(generateinvalidID);
	}

}
