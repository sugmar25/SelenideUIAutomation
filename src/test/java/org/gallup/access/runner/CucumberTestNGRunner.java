package org.gallup.access.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"hooks","org/gallup/access/stepdefinations"},
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"},
        monochrome = true,
        tags = "@regression"
)
public class CucumberTestNGRunner extends AbstractTestNGCucumberTests {
}
