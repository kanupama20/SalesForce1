package salesForceNew.test.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import salesForceNew.test.utility.CommonUtilities;

public class ReadProperties {

	public static String readPropertyData(String propertyfilenm,String key) throws IOException{
		/*
		 * FileInputStream fis=new FileInputStream(new File(
		 * "C:\\Users\\kanup\\eclipse-workspace\\SalesForce-New\\src\\test\\resources\\data.properties"
		 * )); Properties ob=new Properties(); ob.load(fis); String
		 * value=ob.getProperty(key); return value;
		 */
		
		CommonUtilities CU = new CommonUtilities();
		Properties applicationPropertiesFile = CU.loadFile(propertyfilenm);
		String value = CU.getApplicationProperty(key, applicationPropertiesFile);
		return value;
	}

/*public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	String path = readPropertyData("url");
	System.out.println(path);*/
}
