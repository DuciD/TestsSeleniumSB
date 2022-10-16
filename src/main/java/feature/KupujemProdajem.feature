Feature: KupujemProdajem

  Scenario: Test project Kupujem Prodajem
    Given a user is on Kupujem Prodajem website
    When a user search for drombulje
    Then the list of selected items are displayed
    And the approximate value for an item has calculated
    When  click on random link of any drombulje item
    Then correct item page is displayed


    When second cheapest item is known
    And user choose random link of any of 5 instruments
    And Check if it is more than one page displayed
    And user click on random instrument link
    Then more information about random instrument has found

