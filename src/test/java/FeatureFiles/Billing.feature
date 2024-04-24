Feature: Billing functionality

  Background:
    Given user is on landingPage


  Scenario: Verify products in bill are same as they were in cart
    Given  user adds different products with different qty
    When   user proceeds to checkout
    Then   billing data should be correct

