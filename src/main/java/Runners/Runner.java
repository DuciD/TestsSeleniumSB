package Runners;

//import cucumber.api.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions(
        features = "src/main/java/feature/KupujemProdajem.feature",
        glue = {"Steps"}
)


public class Runner extends AbstractTestNGCucumberTests {
}
