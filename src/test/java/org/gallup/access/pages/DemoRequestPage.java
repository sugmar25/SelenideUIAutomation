package org.gallup.access.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.Val;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class DemoRequestPage {
    private SelenideElement fullName = $(By.id("fullName"));
    private SelenideElement jobTitle = $(By.id("jobBand"));
    private SelenideElement emailAddress =$(By.id("emailAddress"));
    private SelenideElement phoneNumber = $(By.id("busPhone"));
    private SelenideElement company = $(By.id("company"));
    private SelenideElement postalCode = $(By.id("zipPostal"));
    private SelenideElement country = $(By.id("country"));
    private SelenideElement primaryReason = $(By.id("customFieldDropdown"));
    private SelenideElement acknowledgement = $x("//input[@type='checkbox']");


    //To fill the basic details
    public void fillTheBasicDetails(String entfFullName, String entEmailAddress, int entPhoneNumber, String entCompannyName, int entPostalCode){
        try{
            //fullName.shouldBe(Condition.visible, Duration.ofSeconds(15)).val(entfFullName);
            fullName.val(entfFullName);
            emailAddress.val(entEmailAddress);
            phoneNumber.val(String.valueOf(entPhoneNumber));
            company.val(entCompannyName);
            postalCode.val(String.valueOf(entPostalCode));
        } catch (Exception e) {
            throw new RuntimeException("unable to fill the basic details", e);
        }
        }

    //select the job title using select option
    public void selectJobtitle(String selJobTitle) {
        try {
            jobTitle.selectOption(selJobTitle);

        } catch (NoSuchElementException e) {
            throw new RuntimeException("unable to select the country", e);
        }
    }

    //select the country using condition with both options
    public void selectCOuntry(String countryName) {
        try {
            ElementsCollection countryList = country.getOptions();
            for (SelenideElement selcountryList : countryList) {
                if (selcountryList.getText().equalsIgnoreCase(countryName)) {
                    country.selectOption(selcountryList.getText());
                    break;
                }
            }
            countryList.stream().filter(findcountryList -> findcountryList.getText().equalsIgnoreCase(countryName)).findFirst().ifPresent(selCountryName -> country.selectOption(selCountryName.getText()));
        } catch (NoSuchElementException e) {
            throw new RuntimeException("unable to select the country", e);
        }

    }

    //Select random reason with int value
    public void selectPrimaryReason(){
        try{
            primaryReason.selectOption(2);

        } catch (NoSuchElementException e) {
            throw new RuntimeException("unable to select the reason",e);
        }
    }

    //Click the accknlogement
    public void clkAccknlogement(){
        try{
            acknowledgement.click();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("unable to click the accknolodgement",e);
        }
    }


}
