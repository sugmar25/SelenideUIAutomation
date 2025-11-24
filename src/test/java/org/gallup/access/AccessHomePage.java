package org.gallup.access;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class AccessHomePage {

    @Test
    public void homePage() {
        Configuration.browser="Chrome";
        Configuration.baseUrl = "https://www.gallup.com/";
        open("access/");
        $(By.xpath("//span[normalize-space()='Request a Demo']")).click();
        String validateHeading = $(By.xpath("(//div[@class='c-topic u-color--brand-dark u-text--bold'])")).getText();
        Configuration.screenshots = true;
        Configuration.reportsFolder = "target/reports/tests";
        Assert.assertEquals(validateHeading, "DEMO REQUEST", "Header validated Successfully");
        ElementsCollection countLink = $$x("//a[@href]");
        Map<String, String> fulllist = countLink.stream().collect(Collectors.toMap(a -> a.getText(), a -> a.getAttribute("href"), (key1, key2) -> key1));
        System.out.println(fulllist.keySet() + "The link for this one is" + fulllist.values());

        System.out.println(countLink.shouldHave(CollectionCondition.sizeGreaterThan(100).because("its having more link")));


    }

    @Test
    public void handelWebTable(){
        open("https://www.tutorialspoint.com/selenium/practice/webtables.php");

        ElementsCollection tablerowID =$$x("//table[@class='table table-striped mt-3']/tbody/tr");

        tablerowID.stream().map(SelenideElement::getText).forEach(System.out::println);
        for(int i=0; i<=tablerowID.size();i++){
            ElementsCollection tableColumn = $$x("//table[@class='table table-striped mt-3']/tbody/tr["+i+"]/td");
            for (int columncount=0; columncount<=tableColumn.size();columncount++){
                SelenideElement getColoumnText = $x("//table[@class='table table-striped mt-3']/tbody/tr["+i+"]/td["+i+"]");
                if(getColoumnText.getText().equalsIgnoreCase("Legal")){
                    $x("//table[@class='table table-striped mt-3']/tbody/tr["+i+"]/td["+i+1+"]").click();
                    break;
                }
            }


        }

    }

    public void handleWebTable() {
        open("https://www.tutorialspoint.com/selenium/practice/webtables.php");

        ElementsCollection tableRows = $$x("//table[@class='table table-striped mt-3']/tbody/tr");

        for (SelenideElement row : tableRows) {
            ElementsCollection columns = row.$$x("./td");

            // Print entire row text
            System.out.println(row.getText());

            for (int colIndex = 0; colIndex < columns.size(); colIndex++) {
                String cellText = columns.get(colIndex).getText();

                if ("Legal".equalsIgnoreCase(cellText)) {
                    // Click the next column if it exists
                    if (colIndex + 1 < columns.size()) {
                        columns.get(colIndex + 1).click();
                    }
                    break;
                }
            }
        }
    }

}
