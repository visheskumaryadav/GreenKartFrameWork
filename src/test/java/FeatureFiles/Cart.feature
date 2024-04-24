Feature: Cart functionality

  Background:
    Given user is on landingPage

  Scenario: Verify added product details in cart is correct
    Given  user adds different products with different qty
    When  user open the cart
    Then  added products details should be correct

  Scenario: Verify products can be removed from the cart
    Given  user adds different products with different qty
    When  user open the cart
    And  user removes all products
    Then Proceed to checkout button is disabled

