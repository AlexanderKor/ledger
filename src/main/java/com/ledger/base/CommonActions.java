package com.ledger.base;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CommonActions {
	WebDriver driver = EnvironmentSettings.getDriver();
	public WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(60))
					.ignoring(StaleElementReferenceException.class);

	public CommonActions() {}

	public void goToUrl(String url) {
		 EnvironmentSettings.getDriver().navigate().to
				 ( url.startsWith("https") ? url : "https://" + url);
	}

	public void refreshThePage() { EnvironmentSettings.getDriver().navigate().refresh(); }
	public void clickOnTheUIElementWithText(String text) {EnvironmentSettings.getDriver().findElement(By.partialLinkText(text)).click(); }
	public WebElement waitForElementByXpath(String xpath) { return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))); }
	public WebElement waitForElementToBeClickable(WebElement element) { return wait.until(ExpectedConditions.elementToBeClickable(element)); }
	public WebElement waitForElementToBeClickable(By locator) { return wait.until(ExpectedConditions.elementToBeClickable(locator)); }
	public WebElement waitForVisibilityOfElement(WebElement element) { return  wait.until(ExpectedConditions.visibilityOf(element));	}
	public List<WebElement> waitForVisibilityOfAllElements(List<WebElement> elements) { return  wait.until(ExpectedConditions.visibilityOfAllElements(elements));	}
	public static String getPageTitle() {return EnvironmentSettings.getDriver().getTitle(); }
	public static String getCurrentUrl() {return EnvironmentSettings.getDriver().getCurrentUrl();	}
}
