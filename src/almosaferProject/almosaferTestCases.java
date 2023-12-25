package almosaferProject;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class almosaferTestCases extends parametersClass {
	@BeforeTest
	@Parameters({ "browser" })
	public void setUp(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		setUpWebsite();
	}

	@Test(description = "Check if the default language is English", priority = 1)
	public void checkWebsiteLanguage() {
		checkLanguage("en");
	}

	@Test(description = "Check if the default currency is SAR", priority = 2)
	public void checkWebsiteCurrency() {
		checkCurrency("SAR");
	}

	@Test(description = "Check if the contact number is correct", priority = 3)
	public void checkWebsiteContactNumber() {
		checkContactNumber("+966554400000");
	}

	@Test(description = "Check if qitaf logo is displayed in the footer", priority = 4)
	public void checkQitafLogoDisplay() {
		checkLogoDisplay(driver.findElement(By.xpath("//div[@class='sc-dznXNo iZejAw']//*[name()='svg']")));
	}

	@Test(description = "Check if hotel tap is NOT selected by default", priority = 5)
	public void checkHotelsTapSelection() {
		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String isHotelTapSelected = hotelTab.getAttribute("aria-selected");
		String expectedisHotelTapSelected = "false";

		softassert.assertEquals(isHotelTapSelected, expectedisHotelTapSelected,
				"Check if the hotel search tap is NOT selected by default");
	}

	@Test(description = "Check if flight departure and arrival dates are correct", priority = 6)
	public void checkDeapartureAndArrivalDate() {
//		Check date
		WebElement departureDate = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-fvLVrH hNjEjT']"));
		WebElement arrivalDate = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-fvLVrH hNjEjT']"));
		checkDate(departureDate, arrivalDate);

//		Check day
		WebElement departureDay = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-eSePXt ljMnJa']"));
		WebElement arrivalDay = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-eSePXt ljMnJa']"));
		checkDay(departureDay, arrivalDay);

//		Check month
		WebElement departureMonth = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-hvvHee cuAEQj']"));
		WebElement arrivalMonth = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-hvvHee cuAEQj']"));
		checkMonth(departureMonth, arrivalMonth);
	}

	@Test(description = "Check searching for hotels process randomly on English and Arabic website", priority = 7)
	@Parameters({ "browser" })
	public void checkSearchHotelsProcess(String browser) throws InterruptedException, IOException {
		String[] websiteUrls = { "https://global.almosafer.com/en", "https://global.almosafer.com/ar" };
		int randomWebsiteIndex = rand.nextInt(websiteUrls.length);
		driver.get(websiteUrls[randomWebsiteIndex]);

		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");

		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		hotelTab.click();

		WebElement autocompleteInput = driver.findElement(By.className("phbroq-2"));

//			Check search process when website's language is English
		if (driver.getCurrentUrl().contains("en")) {
			Assert.assertEquals(actualLanguage, "en");

			autocompleteInput.sendKeys(enRandomLocations[enRandomIndex]);

			List<WebElement> autocompleteList = driver.findElements(By.className("phbroq-5"));
			WebElement firstResult = autocompleteList.get(0);
			firstResult.click();

//				Randomly select a reservation option
			WebElement hotelReservationOptions = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));
			Select selectReservationOption = new Select(hotelReservationOptions);

			int reservationRandomIndex = rand.nextInt(2);
			selectReservationOption.selectByIndex(reservationRandomIndex);

//				If user choose more options, randomly select values
			if (reservationRandomIndex == 2) {
				// Randomly select number of adults option
				WebElement adultsOptions = driver
						.findElement(By.cssSelector("div[class='ki9ony-9 jNPGbR'] select[class='ki9ony-11 iwteRh']"));
				List<WebElement> adultsOptionsValues = adultsOptions.findElements(By.tagName("option"));
				Select selectAdultsOption = new Select(adultsOptions);
				int adultsRandomIndex = rand.nextInt(adultsOptionsValues.size());
				selectAdultsOption.selectByIndex(adultsRandomIndex);
				Thread.sleep(1000);

				// Randomly select number of children option
				WebElement childrenOptions = driver
						.findElement(By.cssSelector("div[class='ki9ony-9 imYSKa'] select[class='ki9ony-11 iwteRh']"));
				List<WebElement> childrenOptionsValues = childrenOptions.findElements(By.tagName("option"));
				Select selectChildrenOption = new Select(childrenOptions);
				int childrenRandomIndex = rand.nextInt(childrenOptionsValues.size());
				selectChildrenOption.selectByIndex(childrenRandomIndex);
				Thread.sleep(1000);

				// if there is children, randomly select age of children option
				if (childrenRandomIndex != 0) {
					WebElement childrenAgeContainer = driver.findElement(By.cssSelector(".ki9ony-16.kVPeYY"));
					List<WebElement> childrenAgeSelectors = childrenAgeContainer
							.findElements(By.className("ki9ony-11"));

					for (int i = 0; i < childrenAgeSelectors.size(); i++) {
						WebElement childrenAge = childrenAgeSelectors.get(i);
						List<WebElement> childrenAgeValues = childrenAge.findElements(By.tagName("option"));

						Select selectChildrenAge = new Select(childrenAge);
						int childrenAgeRandomIndex = rand.nextInt(childrenAgeValues.size());
						selectChildrenAge.selectByIndex(childrenAgeRandomIndex);
						Thread.sleep(1000);
					}
				}
			}
		}

//			Check search process when website's language is Arabic
		if (driver.getCurrentUrl().contains("ar")) {
			Assert.assertEquals(actualLanguage, "ar");

			autocompleteInput.sendKeys(arRandomLocations[arRandomIndex]);

			List<WebElement> autocompleteList = driver.findElements(By.className("phbroq-5"));
			WebElement firstResult = autocompleteList.get(0);
			firstResult.click();

//				Randomly select a reservation option
			WebElement hotelReservationOptions = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));
			Select selectReservationOption = new Select(hotelReservationOptions);

			int reservationRandomIndex = 2;
			selectReservationOption.selectByIndex(reservationRandomIndex);

//				If user choose more options, randomly select values
			if (reservationRandomIndex == 2) {
				// Randomly select number of adults option
				WebElement adultsOptions = driver
						.findElement(By.cssSelector("div[class='ki9ony-9 beEFlC'] select[class='ki9ony-11 iwteRh']"));
				List<WebElement> adultsOptionsValues = adultsOptions.findElements(By.tagName("option"));
				Select selectAdultsOption = new Select(adultsOptions);
				int adultsRandomIndex = rand.nextInt(adultsOptionsValues.size());
				selectAdultsOption.selectByIndex(adultsRandomIndex);
				Thread.sleep(1000);

				// Randomly select number of children option
				WebElement childrenOptions = driver
						.findElement(By.cssSelector("div[class='ki9ony-9 ihCYgq'] select[class='ki9ony-11 iwteRh']"));
				List<WebElement> childrenOptionsValues = childrenOptions.findElements(By.tagName("option"));
				Select selectChildrenOption = new Select(childrenOptions);
				int childrenRandomIndex = rand.nextInt(childrenOptionsValues.size());
				selectChildrenOption.selectByIndex(childrenRandomIndex);
				Thread.sleep(1000);

				// if there is children, randomly select age of children option
				if (childrenRandomIndex != 0) {
					WebElement childrenAgeContainer = driver.findElement(By.cssSelector(".ki9ony-16.ACKOo"));
					List<WebElement> childrenAgeSelectors = childrenAgeContainer
							.findElements(By.className("ki9ony-11"));

					for (int i = 0; i < childrenAgeSelectors.size(); i++) {
						WebElement childrenAge = childrenAgeSelectors.get(i);
						List<WebElement> childrenAgeValues = childrenAge.findElements(By.tagName("option"));

						Select selectChildrenAge = new Select(childrenAge);
						int childrenAgeRandomIndex = rand.nextInt(childrenAgeValues.size());
						selectChildrenAge.selectByIndex(childrenAgeRandomIndex);
						Thread.sleep(1000);
					}
				}
			}
		}

//		Click on search hotels button
		WebElement searchButton = driver.findElement(By.className("sc-1vkdpp9-6"));
		searchButton.click();

//		Check for search results loading completely
		WebElement searchResultsMessage = driver.findElement(By.className("sc-cClmTo"));
		if (driver.getCurrentUrl().contains("ar")) {
			Assert.assertEquals(searchResultsMessage.getText().contains("وجدنا"), true);
			
			// Click on the lowest price sort button
			WebElement lowestPriceButton = driver.findElement(By.cssSelector(".sc-hokXgN.kgqEve, .sc-hokXgN.kgqEve[data-testid='HotelSearchResult__sort__LOWEST_PRICE']"));
			lowestPriceButton.click();
		}
		if (driver.getCurrentUrl().contains("en")) {
			Assert.assertEquals(searchResultsMessage.getText().contains("found"), true);
			
			// Click on the lowest price sort button
			WebElement lowestPriceButton = driver.findElement(By.className("sc-hokXgN"));
			lowestPriceButton.click();
		}

//		Check if the results are sorted from the lowest to highest
		List<WebElement> finalPrices = driver.findElements(By.className("PriceDisplay__FinalRate"));
		String lowestPriceString = finalPrices.get(0).findElement(By.className("Price__Value")).getText();
		String highestPriceString = finalPrices.get(finalPrices.size() - 1).findElement(By.className("Price__Value"))
				.getText();
		
		System.out.println(finalPrices.size() + "results with prices were found");

		// if the number is with commas, remove the commas before parsing it
		if (lowestPriceString.contains(",")) {
			lowestPriceString = lowestPriceString.replace(",", "");
		}
		int lowestPrice = Integer.parseInt(lowestPriceString);

		if (highestPriceString.contains(",")) {
			highestPriceString = highestPriceString.replace(",", "");
		}
		int highestPrice = Integer.parseInt(highestPriceString);

		if (finalPrices.size() == 1) {
			System.out.println("only one price is found");
		}

//		Take a screenshot of search results
		takeScreenshot("./screenshots/" + browser + "_screenshot.png");

		Assert.assertEquals(lowestPrice < highestPrice, true);
		System.out.println("The results are sorted from lowest price which is " + lowestPrice
				+ "$ to highest price which is " + highestPrice + "$");
	}

	@AfterTest
	public void postTesting() {
		softassert.assertAll();
		
		driver.quit();
	}
}
