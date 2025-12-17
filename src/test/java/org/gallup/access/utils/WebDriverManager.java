package org.gallup.access.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {

    // Step 1: Singleton instance
    private static WebDriverManager instance;

    // Step 2: ThreadLocal driver for parallel safety
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Step 3: Private constructor
    private WebDriverManager() {}

    // Step 4: Singleton accessor
    public static synchronized WebDriverManager getInstance() {
        if (instance == null) {
            instance = new WebDriverManager();
        }
        return instance;
    }

    // Step 5: Driver accessor
    public WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(new ChromeDriver());
        }
        return driver.get();
    }

    // Step 6: Quit driver safely
    public void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // clean up ThreadLocal
        }
    }
}