package testCases;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.HomePage;
import base.BasePage;


public class HomePageTest extends BasePage {
	
	@BeforeTest
	public void setInfo() {
	
		testName="VerifyFounderAndChairmanName";
		testDesc="VerifyFounderAndChairmanNameInHclCorporationPage";
		author="Jaydeep Sau";
		category="Assignment";
	}
	
	@Test
	public void runVerifyFounderAndChairmanNameInHclCorporationPage() throws IOException {
		
	new HomePage(driver,test,node)
		.navigateToLeadershipPage()
		.navigateToHclCorporationPage()
		.verifyFounderAndChairmanNameInHclCorporationPage();
		
		
	}

}
