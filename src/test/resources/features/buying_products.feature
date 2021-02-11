Feature: Use Shopping Cart

  @BuyProduct
  Scenario: Buying products at SwagLabs
    Given that Juan is login on SwagLabs app
    When He puts in the cart the Sauce Labs Backpack
    And He does the checkout of his purchase
    Then He should see that his purchase is successful

  @BuyProducts
  Scenario: Buying differents products at SwagLabs
    Given that Juan is login on SwagLabs app
    When He puts in the cart these products
      | products                   |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light |
      | Sauce Labs Fleece Jacket |
    And He does the checkout of his purchase
    Then He should see the message CHECKOUT: COMPLETE!

