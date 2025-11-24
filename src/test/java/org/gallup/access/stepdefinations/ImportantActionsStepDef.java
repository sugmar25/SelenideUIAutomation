package org.gallup.access.stepdefinations;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.FileNotDownloadedError;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import org.gallup.access.utils.AbstractComponent;
import org.gallup.access.utils.DBConnectionManager;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ImportantActionsStepDef extends AbstractComponent {
    @Given("Open webpage and access web table")
    public void open_webpage_and_access_web_table() throws InterruptedException {
        open("https://www.tutorialspoint.com/selenium/practice/webtables.php");

        ElementsCollection tablerowID = $$x("//table[@class='table table-striped mt-3']/tbody/tr");
        System.out.println(tablerowID.size());

        tablerowID.stream().map(SelenideElement::getText).forEach(System.out::println);
        for (int i = 1; i <= tablerowID.size(); i++) {
            ElementsCollection tableColumn = $$x("//table[@class='table table-striped mt-3']/tbody/tr[" + i + "]/td");
            for(int columncount = 1; columncount <= tableColumn.size(); columncount++) {
                SelenideElement getColoumnText = $x("//table[@class='table table-striped mt-3']/tbody/tr[" + i + "]/td[" + columncount + "]");

                if (getColoumnText.getText().equalsIgnoreCase("Legal")) {
                    $x("//table[@class='table table-striped mt-3']/tbody/tr[" + i + "]/td[" + (columncount + 1) + "]").click();
                    break;
                }

            }
        }
//        ElementsCollection totalColumn =$$x("//table[@class='table table-striped mt-3']/tbody/tr/td");
//        System.out.println(totalColumn.size());
//        totalColumn.stream().filter(a->a.getText().equalsIgnoreCase("Legal"));
//        IntStream.range(0,totalColumn.size()).filter(a->totalColumn.get(a).getText().equalsIgnoreCase("Legal"))
//                .forEach(a->{$x("//table[@class='table table-striped mt-3']/tbody/tr/td[" + (a + 1) + "]").click(); });

    }

    @Given("Open webpage and performdouble click")
    public void openWebpageAndPerformdoubleClick() {
        try {
            //action.movetoelement for selecting submenu
            $x("//a[text()='Click Here for Broken Link']").click();
            open("https://www.tutorialspoint.com/selenium/practice/buttons.php");
            SelenideElement performRightClick = $x("(//button[text()='Right Click Me'])");
            actions().contextClick(performRightClick).build().perform();
            Thread.sleep(5000);

            //perfom double click
            SelenideElement performDoubleClick = $x("(//button[text()='Double Click Me'])");
            actions().doubleClick(performDoubleClick).build().perform();
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Given("Handel new tab and new windows")
    public void handelNewTabAndNewWindows() throws InterruptedException {
        Configuration.browser="firefox";
        //same ie and safari
        open("https://www.tutorialspoint.com/selenium/practice/browser-windows.php");
        $x("//button[@title=\"New Tab\"]").click();
//        switchTo().window(1, Duration.ofSeconds(1));
//        Thread.sleep(5000);
//        $x("//h1[text()='New Tab']").should(Condition.visible,Duration.ofSeconds(1));
        Set <String> windowsHandel = WebDriverRunner.getWebDriver().getWindowHandles();
        for(String newwindows : windowsHandel ){
            String titleNeme= switchTo().window(newwindows).getTitle();
            if(titleNeme.equalsIgnoreCase("Selenium Practice - Web Tables")) {
                switchTo().window(newwindows);
                $x("//h1[text()='New Tab']").should(Condition.visible,Duration.ofSeconds(1));
            }

        }
        // Get all open window handles
//        Set<String> windowHandles = getWebDriver().getWindowHandles();
//
//        // Convert to a list to access by index
//        List<String> tabs = new ArrayList<>(windowHandles);
//
//        // Check if 5 tabs are open
//        if (tabs.size() >= 5) {
//            // Switch to the 5th tab (index 4, since it's 0-based)
//            getWebDriver().switchTo().window(tabs.get(4));
//        } else {
//            throw new RuntimeException("Less than 5 tabs are open. Found: " + tabs.size());
//        }

    }

    @Given("Scrool down and up")
    public void scroolDownAndUp() {
        try {
            open("https://www.tutorialspoint.com/selenium/practice/scroll-top.php");
//            executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
//            // Scroll down by 500 pixels
//            executeJavaScript("window.scrollBy(0, 500);");
//
//            // Scroll up by 500 pixels
//            executeJavaScript("window.scrollBy(0, -500);");
            SelenideElement bottomOject = $x("/html/body/main/div/div/div[2]/p[11]");
            bottomOject.scrollIntoView(true);
            Thread.sleep(5000);
            SelenideElement topOject = $x("/html/body/main/div/div/div[2]/h3[1]");
            topOject.scrollIntoView(true);
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Given("Perfrom date handel")
    public void perfromDateHandel() {
        try {
            open("https://www.tutorialspoint.com/selenium/practice/date-picker.php");
            $(By.id("datetimepicker1")).click();
            SelenideElement pickMonth = $x("(//select[@aria-label='Month'])[1]");
            pickMonth.selectOptionByValue("June");
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Given("Dowload the pdf and read line by line then verify them")
    public void dowloadThePdfAndReadLineByLineThenVerifyThem() {
        try {
            open("https://www.gallup.com/cliftonstrengths/en/403127/cliftonstrengths-34-report.aspx");

            SelenideElement downloadLink = $x("(//a[@class='btn btn-primary-ghost'])[1]"); // adjust selector
            File pdfFile = downloadLink.download();// Selenide handles wait
            $x("(//a[@class='btn btn-primary-ghost'])[1]").uploadFile();

            String content = readPdf(pdfFile);
            System.out.println("PDF Content:\n" + content);

            // Example assertion
            assert content.contains("CliftonStrengths");

            //connecting to database
//            DBConnectionManager connectionManager = DBConnectionManager.getInstance();
//            connectionManager.connectToDatabase();
        } catch (FileNotDownloadedError e) {
            throw new RuntimeException("Unable to read the file",e);
        }

    }
}
