package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/FeatureFiles",glue = {"StepDefinitionFiles" ,"Hooks"})
public class TestRunner extends AbstractTestNGCucumberTests {

}
