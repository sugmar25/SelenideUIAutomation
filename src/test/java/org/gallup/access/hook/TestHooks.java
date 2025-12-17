package org.gallup.access.hook;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.gallup.access.utils.DBConnectionManager;
import org.gallup.access.utils.WebDriverManager;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestHooks {

    @Before
    public static void setup() {
        Configuration.browser = System.getProperty("selenide.browser", "chrome");
        Configuration.headless = false;
        //WebDriverRunner.getWebDriver().manage().window().maximize();
        WebDriverManager.getInstance().getDriver();
        DBConnectionManager.getInstance().connectToDatabase();
    }

    @After
    public void teardown(){
        closeWebDriver();
    }
}
