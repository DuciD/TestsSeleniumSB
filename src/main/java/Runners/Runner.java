package Runners;

//import cucumber.api.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions(
        features = "src/main/java/feature/KP.feature",
        glue = {"Steps"}
)


public class Runner extends AbstractTestNGCucumberTests {
}
