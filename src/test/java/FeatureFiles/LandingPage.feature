Feature: Product Listing should be correct
  Background:
    Given user is on landingPage

  Scenario: Verify that product's details are correct with test data
    Given all products appears
    When  product details are same as jsonData
    And  product images are not broken
