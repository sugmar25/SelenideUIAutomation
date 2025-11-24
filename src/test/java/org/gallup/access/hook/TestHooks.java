package org.gallup.access.hook;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestHooks {

    @Before
    public static void setup() {
        Configuration.browser = System.getProperty("selenide.browser", "chrome");
        Configuration.headless = false;
        //WebDriverRunner.getWebDriver().manage().window().maximize();

    }

    @After
    public void teardown(){
        closeWebDriver();
    }
}
