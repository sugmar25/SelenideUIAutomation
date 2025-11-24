package org.gallup.access.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class AccessHomePage {

private SelenideElement requestDemoBtn = $x("//span[text()='Request a Demo']");

public void clickReqDemoBtn(){
    requestDemoBtn.shouldBe(clickable, Duration.ofSeconds(2)).click();
}
}
