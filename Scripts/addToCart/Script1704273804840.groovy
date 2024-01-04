import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

Mobile.waitForElementPresent(findTestObject('Main Screen/countryDropDown-Menu'), GlobalVariable.TimeOut)

Mobile.tap(findTestObject('Main Screen/countryDropDown-Menu'), 2)

Mobile.waitForElementPresent(findTestObject('Main Screen/availableCountries-lst'), GlobalVariable.TimeOut)

KeywordUtil.logInfo(('Selecting ' + country) + ' as Country')

CustomKeywords.'customkeywords.Keywords.selectSpecificElementFromWithinList'(findTestObject('Main Screen/availableCountries-lst'), 
    country, GlobalVariable.action.get(1))

KeywordUtil.logInfo('Setting name to : ' + name)

Mobile.setText(findTestObject('Main Screen/name-Fld'), name, 2)

KeywordUtil.logInfo('Selecting Female Radio Button')

Mobile.tap(findTestObject('Main Screen/female-Radio-Btn'), 2)

KeywordUtil.logInfo('Clicking on Lets Shop Button')

Mobile.tap(findTestObject('Main Screen/letsShop-Btn'), 2)

String[] products = findTestData('Purchase/Add To Cart').getValue('productName', 1).split('#')

KeywordUtil.logInfo('Adding the Following Products : ' + products)

for (String prd : products) {
    try {
        Mobile.tap(findTestObject('Products/addToCart-Btn', [('prd') : prd]), 1)
    }
    catch (Exception e) {
		Mobile.scrollToText(prd)
		Mobile.tap(findTestObject('Products/addToCart-Btn', [('prd') : prd]), 1)
    } 
}

KeywordUtil.logInfo('Clicking on Cart Button')

Mobile.tap(findTestObject('Products/cart-Btn'), 2)

Mobile.waitForElementPresent(findTestObject('Products/cart-Title'), GlobalVariable.TimeOut)

KeywordUtil.logInfo('Validating Selected Products ... ')

for (String prd : products) {
    try {
        Mobile.verifyElementText(findTestObject('Products/productName-Fld', [('prd') : prd]), prd)
    }
    catch (Exception e) {
        Mobile.scrollToText(prd)
		Mobile.verifyElementText(findTestObject('Products/productName-Fld', [('prd') : prd]), prd)
    } 
}