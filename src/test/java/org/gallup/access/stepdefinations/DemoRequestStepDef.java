package org.gallup.access.stepdefinations;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.gallup.access.pages.AccessHomePage;
import org.gallup.access.pages.DemoRequestPage;
import org.gallup.access.utils.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.Configuration;


public class DemoRequestStepDef {

    AccessHomePage accessHomePage = new AccessHomePage();
    DemoRequestPage demoRequestPage = new DemoRequestPage();
    private static final Logger logger = LoggerFactory.getLogger(DemoRequestStepDef.class);



    @Given("Open the Access page and click on the request")
    public void openTheAccessPageAndClickOnTheRequest() {
        try{
            open("https://www.gallup.com/access/");
            accessHomePage.clickReqDemoBtn();
            $x("//button[@name='consent' and contains(text(),'Accept')]").click();
            logger.info("able to click the button successfully");
        } catch (Exception e) {
            throw new RuntimeException("unable to open the page",e);
        }
    }

    @When("Fill the required detail with name {string} email Id {string} phoneNo {int} company nams {string} and postancode {int}")
    public void fillTheRequiredDetailWithNameEmailIdPhoneNoCompanyNamsAndPostancode(String name, String emailId, int phoneNo, String companyName, int postCode) {
        try {
            demoRequestPage.fillTheBasicDetails(name,emailId,phoneNo,companyName,postCode);
            logger.info("Fill the details {} name {} phoneNo {} companyName",name,phoneNo,companyName);
        } catch (Exception e) {
            throw new RuntimeException("unable to fill the details", e);
        }
    }

    @And("Select the job title as {string}")
    public void selectTheJobTitleAs(String jobTitle) {
        try {
            demoRequestPage.selectJobtitle(jobTitle);
        } catch (Exception e) {
            throw new RuntimeException("unable to select the job title", e);
        }
    }

    @And("Select the country as {string}")
    public void selectTheCountryAs(String country) {
        try {
            demoRequestPage.selectCOuntry(country);
        } catch (Exception e) {
            throw new RuntimeException("unable to select the country", e);
        }

    }

    @And("Select the personal reason as {string}")
    public void selectThePersonalReasonAs(String personalReason) {
        try {
            demoRequestPage.selectPrimaryReason();
        } catch (Exception e) {
            throw new RuntimeException("Unable to select the primary reason",e);
        }
    }

    @Then("Click on the accknlogement")
    public void clickOnTheAccknlogement() {
        try {
            demoRequestPage.clkAccknlogement();
        } catch (Exception e) {
            throw new RuntimeException("unable to click the ack", e);
        }
    }
}
