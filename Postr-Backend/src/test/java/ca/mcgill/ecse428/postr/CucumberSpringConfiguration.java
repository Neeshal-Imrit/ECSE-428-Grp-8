package ca.mcgill.ecse428.postr;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ca.mcgill.ecse428.postr.stepdefinitions")  // Ensure your step definitions are scanned
public class CucumberSpringConfiguration {
}
