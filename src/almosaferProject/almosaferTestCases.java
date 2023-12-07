package almosaferProject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class almosaferTestCases {

	String website = "https://global.almosafer.com/en";
	WebDriver driver = new ChromeDriver();
	SoftAssert softassert = new SoftAssert();

	@BeforeTest
	public void setUp() {
		driver.manage().window().maximize();
		driver.get(website);
		WebElement welcomeScreen = driver.findElement(By.className("cta__saudi"));
		welcomeScreen.click();
	}

	@Test(enabled = false)
	public void checkTheLanguage() {
		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		String expectedLanguage = "en";
		
		softassert.assertEquals(actualLanguage, expectedLanguage, "Check if the default language is English");
	}
	
	@Test(enabled = false)
	public void checkTheCurrency() {
		WebElement currencyElement = driver.findElement(By.cssSelector(".sc-dRFtgE.fPnvOO"));
		String actualCurrency = currencyElement.getText();
		String expectedCurrency = "SAR";
		
		softassert.assertEquals(actualCurrency, expectedCurrency, "Check if the default curency is SAR");
	}
	
	@Test(enabled = false)
	public void checkTheContactNumber() {
		WebElement contactNumberElement = driver.findElement(By.cssSelector("a[class='sc-hUfwpO bWcsTG'] strong"));
		String actualContactNumber = contactNumberElement.getText();
		String expectedContactNumber = "+966554400000";
		
		softassert.assertEquals(actualContactNumber, expectedContactNumber, "Check if the contact number is correct");
	}
	
	@Test(enabled = false)
	public void checkQitafLogo() {
		WebElement qitafLogoElement = driver.findElement(By.xpath("//div[@class='sc-dznXNo iZejAw']//*[name()='svg']"));
		boolean isLogoDisplayed = qitafLogoElement.isDisplayed();
		boolean expectedisLogoDisplayed = true;
		
		softassert.assertEquals(isLogoDisplayed, expectedisLogoDisplayed, "Check if qitaf logo is displayed in the footer");
	}
	
	@Test(enabled = false)
	public void checkHotelSearchTap() {
		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String isHotelTapSelected = hotelTab.getAttribute("aria-selected");
		String expectedisHotelTapSelected = "false";
		
		softassert.assertEquals(isHotelTapSelected, expectedisHotelTapSelected, "Check if the hotel search tap is NOT selected by default");
	}

	@Test(enabled = false)
	public void checkFlightsDeapartureAndArrivalDate() {
		LocalDate date = LocalDate.now();

//		Check date
		WebElement departureDate = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-fvLVrH hNjEjT']"));
		int actualDepartureDate = Integer.parseInt(departureDate.getText());
		int expectedDepartureDate = date.plusDays(1).getDayOfMonth();
		
		Assert.assertEquals(actualDepartureDate, expectedDepartureDate);
		
		WebElement arrivalDate = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-fvLVrH hNjEjT']"));
		int actualArrivalDate = Integer.parseInt(arrivalDate.getText());
		int expectedArrivalDate = date.plusDays(2).getDayOfMonth();
		
		Assert.assertEquals(actualArrivalDate, expectedArrivalDate);
		
//		Check day
		WebElement departureDay = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-eSePXt ljMnJa']"));
		String actualDepartueDay = departureDay.getText();
		String expectedDepartueDay = date.plusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		
		Assert.assertEquals(actualDepartueDay, expectedDepartueDay);
		
		WebElement arrivalDay = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-eSePXt ljMnJa']"));
		String actualArrivalDay = arrivalDay.getText();
		String expectedArrivalDay = date.plusDays(2).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	
		Assert.assertEquals(actualArrivalDay, expectedArrivalDay);
		
//		Check month
		WebElement departureMonth = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-hvvHee cuAEQj']"));
		String actualDepartueMonth = departureMonth.getText();
		String expectedDepartueMonth = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		
		Assert.assertEquals(actualDepartueMonth, expectedDepartueMonth);
		
		WebElement arrivalMonth = driver.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-hvvHee cuAEQj']"));
		String actualArrivalMonth = arrivalMonth.getText();
		String expectedArrivalMonth = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	
		Assert.assertEquals(actualArrivalMonth, expectedArrivalMonth);
	}
	
	@Test(enabled = false)
	public void checkChangingLanguage() {
		WebElement languageSwitch = driver.findElement(By.className("sc-gkFcWv"));
		String currentActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		
		languageSwitch.click();
		
		String newActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		if(currentActualLanguage.contains("en")) {
			Assert.assertEquals(newActualLanguage, "ar");
		}
		
		if(currentActualLanguage.contains("ar")) {
			Assert.assertEquals(newActualLanguage, "en");
		}
	}
	
	@Test()
	public void chooseFirstAutocompleteResult() {
		String currentLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");

		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		hotelTab.click();
		
		WebElement autocompleteInput = driver.findElement(By.className("phbroq-2"));
		String [] enRandomLocations = {"Dubai", "Jeddah", "Riyadh"};
		String [] arRandomLocations = {"دبي", "جدة", "الرياض"};
		
		Random rand = new Random();
		int enRandomIndex = rand.nextInt(enRandomLocations.length);
		int arRandomIndex = rand.nextInt(arRandomLocations.length);
		
		if (currentLanguage.contains("en")) {
			autocompleteInput.sendKeys(enRandomLocations[enRandomIndex]);
		}
		if (currentLanguage.contains("ar")) {
			autocompleteInput.sendKeys(arRandomLocations[arRandomIndex]);
		}
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		List<WebElement> autocompleteList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("phbroq-5")));
		WebElement firstResult = autocompleteList.get(0);
		firstResult.click();
	}
	
	@Test
	public void selectNumberOfRoomsAndSearch() {
		WebElement hotelReservation = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));
		Select hotelReservationOptions = new Select(hotelReservation);
		Random rand = new Random();
		int optionsRandomIndex = rand.nextInt(2);
		
		hotelReservationOptions.selectByIndex(optionsRandomIndex);
		
		WebElement searchButton = driver.findElement(By.className("sc-1vkdpp9-6"));
		searchButton.click();
	}
	
	@AfterTest
	public void postTesting() {
		softassert.assertAll();
	}
}
