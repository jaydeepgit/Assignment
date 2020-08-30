package Pages;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import base.BasePage;


public class HomePage extends BasePage {
		
	public HomePage(ChromeDriver driver,ExtentTest test,ExtentTest node) {
		this.driver = driver;
		this.test = test;
		this.node = node;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath="//a[@class='dropdown-toggle']")
	WebElement elementToMouseHover;
	
	@FindBy(xpath="(//li[@class='leaf']//a)[2]")
	WebElement elementForLeadership;
	
	@FindBy(xpath="//a[contains(normalize-space(),'HCL Corporation')]")
	WebElement elementForHclCorporation;
	
	@FindBy(xpath="//span[@class='designation-inner']//p[1]")
	WebElement elementForfounder;
	
	public HomePage navigateToLeadershipPage() throws IOException {

		try {

			Actions action=new Actions(driver);
			action.moveToElement(elementToMouseHover).build().perform();
			elementForLeadership.click();
			
			reportStep( "Navigated to Leadership page successfully", "pass");
		} catch (Exception e) {
			reportStep("Unable to navigate Leadership page", "fail");
		}

		return this;

	}

	public HomePage navigateToHclCorporationPage() throws IOException {

		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,250)");
			Thread.sleep(1000);
			elementForHclCorporation.click();
			
			reportStep( "Navigated to Hcl-Corporation page successfully", "pass");
		} catch (Exception e) {
			reportStep("Unable to navigate Hcl-Corporation page", "fail");
		
		}

		return this;
	}

	public HomePage verifyFounderAndChairmanNameInHclCorporationPage() throws IOException {
		try {
			JavascriptExecutor js1 = (JavascriptExecutor) driver;
			js1.executeScript("window.scrollBy(0,500)");
			String sentence=elementForfounder.getText();
			String[] lines = sentence.split("\n"); 
			String lastLineContent = lines[lines.length - 1];
			Assert.assertEquals(lastLineContent, "Founder & Chairman - Shiv Nadar Foundation");
				
			reportStep("Founder And Chairman name is available in Hcl-Corporation Page", "pass");
		} catch (Exception e) {
			reportStep("Founder And Chairman name is missing in Hcl-Corporation Page", "fail");
		}
		return this;

	}

}
