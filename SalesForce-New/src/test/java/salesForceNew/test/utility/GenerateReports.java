package salesForceNew.test.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class GenerateReports {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	private static GenerateReports ob;
	
	private GenerateReports() {
		
	}
	
	public static GenerateReports getInstance() {
		if(ob==null) {
			ob=new GenerateReports();
		}
		return ob;		
	}
	
	public void startExtentReport() {
		htmlReporter = new ExtentHtmlReporter(Constants.GENERATE_REPORT_PATH);
		extent = new ExtentReports();
		htmlReporter.config().setDocumentTitle("Test Execution Report");
		htmlReporter.config().setReportName("Salesforce regression tests");
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "Salesforce");
		extent.setSystemInfo("Environment", "Automation Testing");
		extent.setSystemInfo("User Name", "Kalyani");
	}
	
	public void startSingleTestReport(String testcaseName) {
		logger = extent.createTest(testcaseName);
	}
	
	public void logTestInfo(String msg) {
		logger.log(Status.INFO, msg);
	}	
	
	public void logTestpassed(String testcaseName) {	
		logger.log(Status.PASS, MarkupHelper.createLabel(testcaseName+" is passTest", null));
	}
	
	public void logTestFailed(String testcaseName) {
		logger.log(Status.FAIL, MarkupHelper.createLabel(testcaseName + "is not passTest", ExtentColor.RED));
	}
	
	public void logTestFailedWithException(Exception e) {
		logger.log(Status.ERROR, e);
	}
	
	public void logTestSkipped(String testcaseName) {
		logger.log(Status.SKIP, MarkupHelper.createLabel(testcaseName+" skipped the test", null));
	}
	
	public void createTest(String testcaseName) {
	logger = extent.createTest(testcaseName);
	}
	
	public void endReport() {
	extent.flush();
	}
}
