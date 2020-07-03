Feature: E2E Test - Inventories

  Background:
    * url baseUrl
    * def inventoryBase = '/inventories'

  Scenario: Create Inventory

    Given path inventoryBase
    And request
    """
      {
        "name" : "Los Vegas Inventory",
        "productLines" : [
          {
            "productId" : "PRODUCT 1",
            "quantity" : 20
          }
        ]
      }
    """
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match $.inventoryId != null

#  Scenario: Get inventory we just created
#
#    Given path inventoryBase + '0'
#    When method GET
#    Then status 200
#    And match response == { firstName: 'John', lastName: 'Doe', age: 30 }
