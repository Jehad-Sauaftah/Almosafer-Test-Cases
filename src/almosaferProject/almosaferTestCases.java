package almosaferProject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class almosaferTestCases extends parametersClass{
	@BeforeTest
	public void setUp() {
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
		
		softassert.assertEquals(isHotelTapSelected, expectedisHotelTapSelected, "Check if the hotel search tap is NOT selected by default");
	}

	@Test(description = "Check if flight departure and arrival dates are correct", priority = 6)
	public void checkDeapartureAndArrivalDate() {
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
	
	@Test(description = "Check searching for hotels process randomly on English and Arabic website", priority = 7)
	public void checkSearchHotelsProcess() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		String [] websiteUrls = {"https://global.almosafer.com/en", "https://global.almosafer.com/ar"};
		int randomWebsiteIndex = rand.nextInt(websiteUrls.length);
		driver.get(websiteUrls[randomWebsiteIndex]);	
		
		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		
		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		hotelTab.click();
		
		WebElement autocompleteInput = driver.findElement(By.className("phbroq-2"));
		
//		Check search process when website's language is English
		if(driver.getCurrentUrl().contains("en")) {
			Assert.assertEquals(actualLanguage, "en");
			
			autocompleteInput.sendKeys(enRandomLocations[enRandomIndex]);
			
			List<WebElement> autocompleteList = driver.findElements(By.className("phbroq-5"));
			WebElement firstResult = autocompleteList.get(0);
			firstResult.click();
			
//			Randomly select a reservation option
			WebElement hotelReservationOptions = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));
			Select selectReservationOption = new Select(hotelReservationOptions);
			
			int reservationRandomIndex = rand.nextInt(2);
			selectReservationOption.selectByIndex(reservationRandomIndex);
			
//			If user choose more options, randomly select values
			if(reservationRandomIndex == 2) {
				// Randomly select number of adults option
				WebElement adultsOptions = driver.findElement(By.cssSelector("div[class='ki9ony-9 jNPGbR'] select[class='ki9ony-11 iwteRh']"));
				List <WebElement> adultsOptionsValues = adultsOptions.findElements(By.tagName("option"));
				Select selectAdultsOption = new Select(adultsOptions);
				int adultsRandomIndex = rand.nextInt(adultsOptionsValues.size());
				selectAdultsOption.selectByIndex(adultsRandomIndex);
				Thread.sleep(1000);
				
				// Randomly select number of children option
				WebElement childrenOptions = driver.findElement(By.cssSelector("div[class='ki9ony-9 imYSKa'] select[class='ki9ony-11 iwteRh']"));
				List <WebElement> childrenOptionsValues = childrenOptions.findElements(By.tagName("option"));
				Select selectChildrenOption = new Select(childrenOptions);
				int childrenRandomIndex = rand.nextInt(childrenOptionsValues.size());
				selectChildrenOption.selectByIndex(childrenRandomIndex);
				Thread.sleep(1000);
				
				// if there is children, randomly select age of children option
				if(childrenRandomIndex != 0) {
					WebElement childrenAgeContainer = driver.findElement(By.cssSelector(".ki9ony-16.kVPeYY"));
					List <WebElement> childrenAgeSelectors = childrenAgeContainer.findElements(By.className("ki9ony-11"));
					
					for (int i = 0; i < childrenAgeSelectors.size(); i++) {
						WebElement childrenAge = childrenAgeSelectors.get(i);
						List <WebElement> childrenAgeValues = childrenAge.findElements(By.tagName("option"));
						
						Select selectChildrenAge = new Select(childrenAge);
						int childrenAgeRandomIndex = rand.nextInt(childrenAgeValues.size());
						selectChildrenAge.selectByIndex(childrenAgeRandomIndex);
						Thread.sleep(1000);
					}
				}
			}
		}
		
//		Check search process when website's language is Arabic
		if(driver.getCurrentUrl().contains("ar")) {
			Assert.assertEquals(actualLanguage, "ar");
			
			autocompleteInput.sendKeys(arRandomLocations[arRandomIndex]);
			
			List<WebElement> autocompleteList = driver.findElements(By.className("phbroq-5"));
			WebElement firstResult = autocompleteList.get(0);
			firstResult.click();
			
//			Randomly select a reservation option
			WebElement hotelReservationOptions = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));
			Select selectReservationOption = new Select(hotelReservationOptions);
			
			int reservationRandomIndex = 2;
			selectReservationOption.selectByIndex(reservationRandomIndex);
			
//			If user choose more options, randomly select values
			if(reservationRandomIndex == 2) {
				// Randomly select number of adults option
				WebElement adultsOptions = driver.findElement(By.cssSelector("div[class='ki9ony-9 beEFlC'] select[class='ki9ony-11 iwteRh']"));
				List <WebElement> adultsOptionsValues = adultsOptions.findElements(By.tagName("option"));
				Select selectAdultsOption = new Select(adultsOptions);
				int adultsRandomIndex = rand.nextInt(adultsOptionsValues.size());
				selectAdultsOption.selectByIndex(adultsRandomIndex);
				Thread.sleep(1000);
				
				// Randomly select number of children option
				WebElement childrenOptions = driver.findElement(By.cssSelector("div[class='ki9ony-9 ihCYgq'] select[class='ki9ony-11 iwteRh']"));
				List <WebElement> childrenOptionsValues = childrenOptions.findElements(By.tagName("option"));
				Select selectChildrenOption = new Select(childrenOptions);
				int childrenRandomIndex = rand.nextInt(childrenOptionsValues.size());
				selectChildrenOption.selectByIndex(childrenRandomIndex);
				Thread.sleep(1000);
				
				// if there is children, randomly select age of children option
				if(childrenRandomIndex != 0) {
					WebElement childrenAgeContainer = driver.findElement(By.cssSelector(".ki9ony-16.ACKOo"));
					List <WebElement> childrenAgeSelectors = childrenAgeContainer.findElements(By.className("ki9ony-11"));
					
					for (int i = 0; i < childrenAgeSelectors.size(); i++) {
						WebElement childrenAge = childrenAgeSelectors.get(i);
						List <WebElement> childrenAgeValues = childrenAge.findElements(By.tagName("option"));
						
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
		}
		if (driver.getCurrentUrl().contains("en")) {
			Assert.assertEquals(searchResultsMessage.getText().contains("found"), true);
		}
		
//		Click on the lowest price sort button
		WebElement lowestPriceButton = driver.findElement(By.className("sc-hokXgN"));
		lowestPriceButton.click();
		
//		Check if the results are sorted from the lowest to highest
		List<WebElement> finalPrices = driver.findElements(By.className("PriceDisplay__FinalRate"));
		String lowestPriceString = finalPrices.get(0).findElement(By.className("Price__Value")).getText();
		String highestPriceString = finalPrices.get(finalPrices.size() - 1).findElement(By.className("Price__Value")).getText();

		// if the number is with commas, remove the commas before parsing it
		if(lowestPriceString.contains(",")) {
			lowestPriceString = lowestPriceString.replace(",", "");
		}
		int lowestPrice = Integer.parseInt(lowestPriceString);
		
		if(highestPriceString.contains(",")) {
			highestPriceString = highestPriceString.replace(",", "");
		}
		int highestPrice = Integer.parseInt(highestPriceString);

		Assert.assertEquals(lowestPrice < highestPrice, true);
		
		System.out.println("The results are sorted from lowest price which is " + lowestPrice + "$ to highest price which is " + highestPrice + "$");
	}
	
	@AfterTest
	public void postTesting() {
		softassert.assertAll();
	}
}
