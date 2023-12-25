package almosaferProject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


public class parametersClass {
	WebDriver driver;
	Random rand = new Random();
	SoftAssert softassert = new SoftAssert();
	LocalDate date = LocalDate.now();
	String website = "https://global.almosafer.com/en";
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
	
	public void checkDate(WebElement departureDate, WebElement arrivalDate) {
		int actualDepartureDate = Integer.parseInt(departureDate.getText());
		int expectedDepartureDate = date.plusDays(1).getDayOfMonth();
		
		Assert.assertEquals(actualDepartureDate, expectedDepartureDate);
		
		int actualArrivalDate = Integer.parseInt(arrivalDate.getText());
		int expectedArrivalDate = date.plusDays(2).getDayOfMonth();
		
		Assert.assertEquals(actualArrivalDate, expectedArrivalDate);
	}
	
	public void checkDay(WebElement departureDay, WebElement arrivalDay) {
		String actualDepartueDay = departureDay.getText();
		String expectedDepartueDay = date.plusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		
		Assert.assertEquals(actualDepartueDay, expectedDepartueDay);
		
		String actualArrivalDay = arrivalDay.getText();
		String expectedArrivalDay = date.plusDays(2).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	
		Assert.assertEquals(actualArrivalDay, expectedArrivalDay);
	}
	
	public void checkMonth(WebElement departureMonth, WebElement arrivalMonth) {
		String actualDepartueMonth = departureMonth.getText();
		String expectedDepartueMonth = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		
		Assert.assertEquals(actualDepartueMonth, expectedDepartueMonth);
		
		String actualArrivalMonth = arrivalMonth.getText();
		String expectedArrivalMonth = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	
		Assert.assertEquals(actualArrivalMonth, expectedArrivalMonth);
	}
	
	public void takeScreenshot(String fileName) throws IOException {
       Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
       
       ImageIO.write(screenshot.getImage(), "PNG", new File(fileName));
	}
}
