package ca.mcgill.ecse428.postr.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleSteps {

    private String status;

    @Given("the application is started")
    public void applicationIsStarted() {
        status = "Running";
    }

    @When("I check the status")
    public void iCheckTheStatus() {
        // Simulating a check (could call an API here)
    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String expectedStatus) {
        assertEquals(expectedStatus, status);
    }
}
