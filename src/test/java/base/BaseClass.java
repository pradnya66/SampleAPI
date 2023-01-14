package base;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import utilities.Config;
import utilities.ExcelReader;

public class BaseClass {
	
	public static final Logger logger = LogManager.getLogger(BaseClass.class.getName());

	public String randomestring()
	{
		String generateinvalidID=RandomStringUtils.random(4);
		return(generateinvalidID);
	}
	
	public Map<String,String> getDataFromExcel(String SheetName, int Rownumber) throws InvalidFormatException, IOException {
		ExcelReader excelReader = new ExcelReader();
		Map<String,String> getData = 
				excelReader.getData(Config.EXCEL, SheetName).get(Rownumber);
		return getData;
	}
	
	

}
