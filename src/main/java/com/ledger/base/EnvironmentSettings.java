package com.ledger.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

import static com.ledger.base.PropertyHandler.openPropertyFile;
import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;

public class EnvironmentSettings {
	private volatile static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	//private static PropertyHandler prop = PropertyHandler.getInstance();

	public static WebDriver getDriver() {
		return drivers.get();
	}

	public static void initDriver() {
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = openPropertyFile(new Properties(), "config.properties").getProperty("browser");
		}
		switch (browser) {
			case "chrome":
				WebDriverManager.getInstance(CHROME).setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("['start-maximized']");
				drivers.set(new ChromeDriver(chromeOptions));
				break;
			case "firefox":
				WebDriverManager.getInstance(FIREFOX).setup();
				drivers.set(new FirefoxDriver());
				drivers.get().manage().window().maximize();
				break;
			default:
				throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
		}
	}

	public static void removeDriver() {
		drivers.get().quit();
		drivers.remove();
	}
}
