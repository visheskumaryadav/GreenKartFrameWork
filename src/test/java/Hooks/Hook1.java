package Hooks;

import TestContext.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class Hook1 {
    TestContext context;
    public Hook1(TestContext context){
        this.context=context;

    }

    @After
    public void after(){
        context.assertAll();
        context.closeBrowser();
    }


}
