Feature: Terms and conditions functionality

  Background:
    Given user is on landingPage
    When  user adds different products with different qty
    And user proceeds to checkout
    Then  billing data should be correct

  Scenario: Verify user is able to place order after accepting terms and conditions
    Given  user place the order
    And user selects country "India"
    And  user accepts the terms and conditions
    When user click on proceed
    Then success message appears

  Scenario: Verify user is able to place order without accepting terms and conditions
    Given  user place the order
    And  user doesn't accepts the terms and conditions
    When user click on proceed
    Then error  message appears