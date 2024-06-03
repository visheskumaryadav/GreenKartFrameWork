package Hooks;

import TestContext.TestContext;
import io.cucumber.java.After;

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
