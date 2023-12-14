package almosaferProject;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class parametersClass {
	String website = "https://global.almosafer.com/en";
	WebDriver driver = new ChromeDriver();
	SoftAssert softassert = new SoftAssert();
	Random rand = new Random();
	String[] enRandomLocations = { "Dubai", "Jeddah", "Riyadh" };
	String[] arRandomLocations = { "دبي", "جدة" };
	int enRandomIndex = rand.nextInt(enRandomLocations.length);
	int arRandomIndex = rand.nextInt(arRandomLocations.length);

	public void setUpWebsite() {
		driver.manage().window().maximize();
		driver.get(website);
		WebElement welcomeScreen = driver.findElement(By.className("cta__saudi"));
		welcomeScreen.click();
	}

	public void checkLanguage(String language) {
		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		String expectedLanguage = language;

		softassert.assertEquals(actualLanguage, expectedLanguage, "Check if the default language is " + language);
	}

	public void checkCurrency(String currency) {
		WebElement currencyElement = driver.findElement(By.cssSelector(".sc-dRFtgE.fPnvOO"));
		String actualCurrency = currencyElement.getText();
		String expectedCurrency = currency;

		softassert.assertEquals(actualCurrency, expectedCurrency, "Check if the default curency is " + currency);
	}

	public void checkContactNumber(String contactNumber) {
		WebElement contactNumberElement = driver.findElement(By.cssSelector("a[class='sc-hUfwpO bWcsTG'] strong"));
		String actualContactNumber = contactNumberElement.getText();
		String expectedContactNumber = contactNumber;

		softassert.assertEquals(actualContactNumber, expectedContactNumber,
				"Check if the contact number is " + contactNumber);
	}

	public void checkLogoDisplay(WebElement logo) {
		WebElement LogoElement = logo;
		boolean isLogoDisplayed = LogoElement.isDisplayed();
		boolean expectedisLogoDisplayed = true;

		softassert.assertEquals(isLogoDisplayed, expectedisLogoDisplayed, "Check if logo is displayed");
	}
}
