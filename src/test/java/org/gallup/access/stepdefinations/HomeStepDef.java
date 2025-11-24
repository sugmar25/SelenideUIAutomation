package org.gallup.access.stepdefinations;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import org.gallup.access.pages.AccessHomePage;
import org.gallup.access.utils.AbstractComponent;
import org.gallup.access.utils.ConfigReader;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.*;

public class HomeStepDef extends AbstractComponent {
    String demoRequestHeader;
    AccessHomePage accessHomePage = new AccessHomePage();
    private static final Logger log = LoggerFactory.getLogger(HomeStepDef.class);


    @Given("Open Gallup Access and Click on request a demo")
    public void open_gallup_access_and_click_on_request_a_demo() {
        try{
            open(ConfigReader.get("base.url"));
            log.info("Page opened successfully");
        } catch (Exception e) {
            throw new RuntimeException("unable to open the page",e);
        }
    }
    @When("Check signIn page is loaded")
    public void check_sign_in_page_is_loaded() {
        try{
            accessHomePage.clickReqDemoBtn();
        } catch (RuntimeException e) {
            throw new RuntimeException("unable to click the element", e);
        }
    }
    @When("Fill the details")
    public void fill_the_details() {
        try{
            SelenideElement demoRequestHeader = $(By.xpath("//div[@class='c-topic u-color--brand-dark u-text--bold']"));
            waitForVisibility(demoRequestHeader,30);
            this.demoRequestHeader = demoRequestHeader.getText();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @Then("Navigate back to the previous page")
    public void navigate_back_to_the_previous_page() {
        try{
            Assert.assertEquals(demoRequestHeader,"DEMO REQUEST", "Header not Matching");
        } catch (RuntimeException e) {
            throw new RuntimeException("Header not Matching",e);
        }
    }

}
