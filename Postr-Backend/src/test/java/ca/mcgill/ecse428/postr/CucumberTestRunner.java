package ca.mcgill.ecse428.postr;

import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;  // Import this for custom configuration

@SuppressWarnings("deprecation")
@Cucumber
@CucumberContextConfiguration
@SpringBootTest
@ContextConfiguration(classes = PostrApplication.class)  // Use the custom configuration
public class CucumberTestRunner {
}
