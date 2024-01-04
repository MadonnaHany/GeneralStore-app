package customkeywords

import java.time.Duration

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.MobileTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.appmanagement.ApplicationState
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption


public class Keywords {

	/**
	 * Method to fetch the current mobile session
	 * @return
	 */
	@Keyword
	def WebDriver getCurrentSessionMobileDriver() {
		return MobileDriverFactory.getDriver();
	}

	/**
	 * Method to perform a vertical swipe
	 * @return
	 */
	@Keyword
	def verticalSwipe() {
		AppiumDriver<?> driver=getCurrentSessionMobileDriver()
		Dimension dim = driver.manage().window().getSize();
		int height = dim.height
		int width = dim.width
		int x = width/2;
		int starty = (int)(height*0.60);
		int endy = (int)(height*0.20);
		TouchAction action = new TouchAction(driver)
		action.press(PointOption.point(x, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(x, endy)).release().perform()
	}

	/**
	 * Method to perform horizontal swipe on elements of a specific list
	 * @param elements
	 * @return
	 */
	@Keyword
	def dynamicHorizontalSwipe(TestObject elements) {
		AppiumDriver<?> driver=getCurrentSessionMobileDriver()
		int y = Mobile.getElementTopPosition(elements,GlobalVariable.TimeOut)
		int startx = Mobile.getDeviceWidth()/2
		int endx = Mobile.getElementLeftPosition(elements,GlobalVariable.TimeOut)
		TouchAction action = new TouchAction(driver)
		action.press(PointOption.point(startx, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(endx, y)).release().perform()
	}
	/**
	 * Method to create test object out of a list of elements
	 * @param elements
	 * @param locator
	 * @return
	 */
	@Keyword
	def TestObject createTestObjectOutOfList(List<MobileElement> elements, String locator) {
		TestObject testObject = new TestObject();
		testObject.addProperty("xpath", ConditionType.EQUALS, locator);
		return testObject;
	}

	/**
	 * \Method to select a specific element from a list
	 * @param elementsList
	 * @param expectedText
	 * @return
	 */
	@Keyword
	def selectSpecificElementFromWithinList(MobileTestObject elementsList, String expectedText, String action){
		try {

			String actualText = ""
			boolean found = false
			String xpath = elementsList.mobileLocator
			AppiumDriver<?> driver=getCurrentSessionMobileDriver()
			while (true) {
				List<MobileElement> elements = driver.findElementsByXPath(xpath)
				for (MobileElement element : elements) {

					actualText = element.getText().trim()
					if (actualText.equalsIgnoreCase(expectedText)) {
						found = true
						element.click()
						break
					}
				}
				if (found == true) {
					break
				}
				else {
					if (action.equalsIgnoreCase(GlobalVariable.action.get(0)))
						dynamicHorizontalSwipe(createTestObjectOutOfList(elements,xpath))
					else
						verticalSwipe()
				}
			}
		}
		catch (Exception e) {
			KeywordUtil.markFailed("Object " + elementsList.getObjectId() + " is not present")
		}
	}
}
