package ca.mcgill.ecse428.postr.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "ca.mcgill.ecse428.postr.cucumber.steps")
public class CucumberTest {
}

