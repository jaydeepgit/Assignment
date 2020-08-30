package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class BasePage {

	public ChromeDriver driver;
	public static Properties prop;
	public static String title;
	public String fileName;
	
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	
	public ExtentTest test,node;
	
	public String testName,testDesc,author,category;
	
	@BeforeSuite
	public void startReport() {
	
				reporter = new ExtentHtmlReporter("./reports/result.html");
				reporter.setAppendExisting(true);
				extent = new ExtentReports();
				extent.attachReporter(reporter);

	}
	
	@BeforeClass
	public void testDetails() {

		test = extent.createTest(testName, testDesc);
		test.assignAuthor(author);
		test.assignCategory(category);

	}
		
	public long takeSnap() throws IOException {
		
		long number =(long) Math.floor(((Math.random()*90000000L)+10000000L));
		
		File source = driver.getScreenshotAs(OutputType.FILE);
		
		File dest = new File("./snaps/image"+number+".png");
		
		FileHandler.copy(source, dest);
		
		return number;
	}
	

	public void reportStep(String msg,String status) throws IOException {

		if(status.equalsIgnoreCase("pass")) {
			node.pass(msg,MediaEntityBuilder.createScreenCaptureFromPath(".././snaps/image"+takeSnap()+".png").build());
		}
		else if(status.equalsIgnoreCase("fail")) {
			node.fail(msg,MediaEntityBuilder.createScreenCaptureFromPath(".././snaps/image"+takeSnap()+".png").build());
			throw new RuntimeException();
		}

	}

	public BasePage(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@BeforeMethod
	public void preCondition() throws IOException {
		node = test.createNode(testName);
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void postCondition() throws IOException {
		driver.close();
	}
	
	
	@AfterSuite
	public void endReport() {
		extent.flush();

	}
	
}
