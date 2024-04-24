package StepDefinitionFiles;

import PageObjects.TermsAndConditions;
import TestContext.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.Ter;

public class TermsAndConditionsDefinition {
    private TestContext context;
    private TermsAndConditions termsAndConditions;
    public TermsAndConditionsDefinition( TestContext context){
        this.context=context;
        termsAndConditions=context.getPageObject().getTermsAndConditions();
    }
    
    @And("user accepts the terms and conditions")
    public void userAcceptsTheTermsAndConditions() {
        termsAndConditions.selectCheckbox();
    }

    @When("user click on proceed")
    public void userClickOnProceed() {
        termsAndConditions.clickProceedBtn();
    }

    @Then("success message appears")
    public void successMessageAppears() {
        String expectedSuccessMsg="Thank you, your order has been placed successfully You'll be redirected to Home page shortly!!";
        context.assertion.assertEquals(termsAndConditions.getSuccessMsg().replaceAll("\\r|\\n", " "),expectedSuccessMsg,"success message mismatch");
    }

    @And("user doesn't accepts the terms and conditions")
    public void userDoesnTAcceptsTheTermsAndConditions() {
        //
    }

    @Then("error  message appears")
    public void errorMessageAppears() {
        context.assertion.assertEquals(termsAndConditions.getErrorMsg(),"Please accept Terms & Conditions - Required","error message mismatch");

    }

    @And("user selects country {string}")
    public void userSelectsCountry(String countryName) {
        termsAndConditions.selectCountry(countryName);
    }
}
