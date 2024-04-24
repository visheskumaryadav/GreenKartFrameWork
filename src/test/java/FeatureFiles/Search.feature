Feature: Search functionality
  Background:
    Given user is on landingPage
    Then user click on search input field

  Scenario: Verify that user is able to search the product by entering full name of product
    When user enter "pista" in search input
    Then that "pista" should appear in result.

  Scenario: Verify that user is able to search products starting with "Cu"
    When  user enter "Cu" in search input
    Then  products starting with "Cu" appears

