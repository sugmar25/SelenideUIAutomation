package org.gallup.access.utils;

import com.codeborne.selenide.*;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;

public class BasePage {

    // Click action with auto-wait
    public void click(SelenideElement element) {
        element.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    // Type text
    public void type(SelenideElement element, String text) {
        element.shouldBe(Condition.visible).setValue(text);
    }

    // Clear field
    public void clearField(SelenideElement element) {
        element.shouldBe(Condition.visible).clear();
    }

    // Get text
    public String getText(SelenideElement element) {
        return element.shouldBe(Condition.visible).getText();
    }

    // Wait for visibility
    public void waitForVisible(SelenideElement element) {
        element.shouldBe(Condition.visible);
    }

    // Wait for clickable
    public void waitForClickable(SelenideElement element) {
        element.shouldBe(Condition.enabled);
    }

    // Scroll
    public void scrollToElement(SelenideElement element) {
        element.scrollTo();
    }

    // Hover
    public void hover(SelenideElement element) {
        element.hover();
    }

    // Dropdown
    public void selectFromDropdown(SelenideElement element, String value) {
        element.selectOption(value);
    }

    // Press Enter
    public void pressEnter(SelenideElement element) {
        element.pressEnter();
    }

    // Upload file
    public void uploadFile(SelenideElement element, File file) {
        element.uploadFile(file);
    }

    // Navigation
    public void openUrl(String url) {
        Selenide.open(url);
    }

    public String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    public void refreshPage() {
        Selenide.refresh();
    }

    public void goBack() {
        Selenide.back();
    }

    public void goForward() {
        Selenide.forward();
    }

    // Page load wait
    public void waitForPageToLoad() {
        Selenide.Wait().until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}
