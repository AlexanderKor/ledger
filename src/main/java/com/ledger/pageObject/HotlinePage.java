package com.ledger.pageObject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.ledger.base.EnvironmentSettings.getDriver;

@Getter
public class HotlinePage extends BasePage{
    private static final String HOME_PAGE_URL = "https://hotline.ua/";
    public HotlinePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input#searchbox.field")
    public WebElement searchBox;

    @FindBy(css = "//div[@class='item-compare']")
    public WebElement itemCompare;

    @FindBy(xpath = "//li[@class='ui-menu-item']")
    public List<WebElement> liveSearchDropDownElementList;

    @FindBy(xpath = "//li[@data-id='prices']/span")
    public WebElement whereToBuy;

    @FindBy(xpath = "//select [@name='sort']")
    public WebElement sortItems;

    @FindBy(xpath = "//a[@data-eventaction='Priceline' and parent::p]")
    public List<WebElement> itemsNamesInSearchResult;

    @FindBy(xpath = "//div[contains(@data-navigation-id, 'app-description')]/div/p")
    public List<WebElement> itemsDescriptionsInSearchResult;

    //shopListElements
    @FindBy(xpath = "//div[@data-selector='price-line']")
    public List<WebElement> shopList;
    @FindBy(xpath = "//div[@data-selector='price-line' and descendant::div//a[contains(@href, 'reviews')]]")
    public List<WebElement> shopListWithReviews;
    @FindBy(xpath = "//div[@data-selector='price-line' and descendant::div//a[contains(@href, 'reviews')]]//div[contains(@class, 'cell-2')]")
    public List<WebElement> guaranteeShopsWithReviews;
    @FindBy(xpath = "//div[@data-selector='price-line' and descendant::div//a[contains(@href, 'reviews')]]//span[@class='price-format' and ancestor::a]")
    public List<WebElement> priceShopsWithReviews;
    @FindBy(xpath = "//div[@data-selector='price-line' and descendant::div//a[contains(@href, 'reviews')]]//a[contains(@href, 'reviews')]")
    public List<WebElement> reviewsShopsWithReviews;
    @FindBy(xpath = "//div[@data-selector='price-line' and descendant::div//a[contains(@href, 'reviews')]]//a[contains(@class, 'btn-orange btn-cell')]")
    public List<WebElement> toShopsLinksWithReviews;

    @Override
    public void openPage(){
        getDriver().navigate().to(HOME_PAGE_URL);
    }

    public static int getIntegerBeforeSpace(String string){
        String result = string.split(" ")[0];
        boolean isInteger = result.matches("-?\\d+");
        return isInteger ? Integer.parseInt(result) : 0;
    }

    public static float convertPriceToFloat(String string){
        return Float.parseFloat(string.replace(",",".").replace(" ", ""));
    }
}
