package ca.mcgill.ecse428.postr.cucumber;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectPackages("ca.mcgill.ecse428.postr.cucumber.stepdefinitions")  // Path to your step definitions package
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "ca.mcgill.ecse428.postr.cucumber.stepdefinitions")
public class CucumberTestRunner {
}
