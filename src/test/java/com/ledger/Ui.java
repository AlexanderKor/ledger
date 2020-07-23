package com.ledger;

import com.ledger.base.CommonActions;
import com.ledger.pageObject.HotlinePage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.ledger.base.EnvironmentSettings.*;
import static com.ledger.base.Utils.getValueFromCsvFile;
import static com.ledger.pageObject.HotlinePage.convertPriceToFloat;
import static com.ledger.pageObject.HotlinePage.getIntegerBeforeSpace;

public class Ui {
    CommonActions commonActions;
    HotlinePage hotlinePage;

    @BeforeMethod
    public void setup() {
        initDriver();
        hotlinePage = new HotlinePage(getDriver());
        commonActions = new CommonActions();
    }

    @AfterMethod
    public void teardown() {removeDriver(); }

    @Test
    public void hotlineTest1() {
        String itemName = "iPhone 11 Pro Max 256GB Dual".toLowerCase();
        String linkToTheCheapestShop = "";
        //Step 1. Open hotline main page
        hotlinePage.openPage();
        //Cookie needed to remove the SHADOW DOM popup
        getDriver().manage().addCookie(new Cookie("gravitecOptInBlocked", "true"));
        //Step 2. Click to the search box and enter item value
        enterToSearchBoxValue(itemName);
        //Step 3. Find from drop down list searched value
        commonActions.waitForVisibilityOfAllElements(hotlinePage.liveSearchDropDownElementList);
        for (int i = 0; i < hotlinePage.liveSearchDropDownElementList.size(); i++) {
            WebElement webElement = hotlinePage.liveSearchDropDownElementList.get(i);
            if (webElement.getText().toLowerCase().contains(itemName)) {
                //commonActions.scrollToElement(webElement);
                webElement.click();
                break;
            }
            //Q1: what should we do if we cannot find any record?
            //i think we should add zero record check case here
        }
        commonActions.waitForVisibilityOfElement(hotlinePage.whereToBuy);
        //Step 4. Push "where to buy"
        //Q2. And what we should do if we cannot find the button because the item is unpopular?
        hotlinePage.whereToBuy.click();
        commonActions.waitForVisibilityOfAllElements(hotlinePage.priceShopsWithReviews);
        //Step 5. Sort shops by numberOfReviews<10 && guaranteeMonth<6 && Reviews > 10 and search cheapest shop
        float minimalPrice = convertPriceToFloat(hotlinePage.priceShopsWithReviews.get(0).getText());
        for (int i = 0; i < hotlinePage.shopListWithReviews.size(); i++) {
            int numberOfReviews = getIntegerBeforeSpace(hotlinePage.reviewsShopsWithReviews.get(i).getText());
            int guaranteeMonth = getIntegerBeforeSpace(hotlinePage.guaranteeShopsWithReviews.get(i).getText());
            float price = convertPriceToFloat(hotlinePage.priceShopsWithReviews.get(i).getText());
            if (numberOfReviews < 10 || guaranteeMonth < 6) {
                continue;
            }
            if (minimalPrice > price) {
                linkToTheCheapestShop = hotlinePage.toShopsLinksWithReviews.get(i).getAttribute("href");
                minimalPrice = price;
            }
            //Q3. Here we should check another don't described case, when all shops don't meet the (step 5) requirements
        }
        System.out.println("Minimal price of " + itemName + " is: " + minimalPrice + " Link to the shop: " + linkToTheCheapestShop);
        //Step 6. Open the cheapest item price shop link and check domain name
        getDriver().get(linkToTheCheapestShop);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("c.ua"));
    }

    @Test
    public void hotLineTest2() throws IOException {
        SoftAssertions softAssertions = new SoftAssertions();
        String pathToCsvWithFirst10Phones = "src\\test\\java\\resources\\testData\\case2.csv";
        //Step 1. Open hotline main page
        hotlinePage.openPage();
        //Step 2. Click to the search box, enter item value and press enter
        enterToSearchBoxValue("iphone");
        hotlinePage.searchBox.sendKeys(Keys.ENTER);
        //Step 3. Sort by number of offers
        commonActions.waitForVisibilityOfElement(hotlinePage.sortItems);
        Select sort = new Select(hotlinePage.sortItems);
        sort.selectByVisibleText("к-ву предложений");
        commonActions.waitForVisibilityOfElement(hotlinePage.sortItems);
        //Step 4. Compare 10 items properties from hotline.sort with 10 items properties from csv
        for (int i = 0; i < 10; i++){
            softAssertions.assertThat(hotlinePage.itemsNamesInSearchResult.get(i).getText()).describedAs("Element " + i + " should have the same name as in csv").contains(getValueFromCsvFile(i+1, 0,  pathToCsvWithFirst10Phones));
            softAssertions.assertThat(hotlinePage.itemsDescriptionsInSearchResult.get(i).getText()).describedAs("Element " + i + " should have the same size of memory as in csv").contains(getValueFromCsvFile(i+1, 1,  pathToCsvWithFirst10Phones));
            softAssertions.assertThat(hotlinePage.itemsDescriptionsInSearchResult.get(i).getText()).describedAs("Element " + i + " should have the same color as in csv").contains(getValueFromCsvFile(i+1, 2,  pathToCsvWithFirst10Phones));
        }
        softAssertions.assertAll();
    }

    public void enterToSearchBoxValue(String itemName){
        commonActions.waitForVisibilityOfElement(hotlinePage.searchBox);
        hotlinePage.searchBox.click();
        hotlinePage.searchBox.sendKeys(itemName);
    }

}
