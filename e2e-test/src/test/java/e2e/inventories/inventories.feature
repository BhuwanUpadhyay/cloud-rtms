Feature: E2E Test - Inventories

  Background:
    * url baseUrl
    * def inventoryBase = '/inventories'
    * def workflowBase = '/workflows'
    * def getTaskInfoByIndex =
    """
    function(o, i) {
      return o.split('?processInstanceId=')[i];
    }
    """

  Scenario: Create Inventory

  # POST ---> create inventory
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
    * def ID = $.inventoryId

  # GET ---> inventory
    Given path inventoryBase,'/',ID
    When method GET
    Then status 200
    And match $.inventoryId == ID
    And match $.name == 'Los Vegas Inventory'
    And match $.productLines[0].quantity == 20
    And match $.productLines[0].productId == 'PRODUCT 1'
    And match $.links[0].rel == 'DataEntry'

  # PUT ---> action
    * def ACTION = $.links[0].path
    And request
    """
      {
        "confirm" : "YES",
        "comment" : "My action."
      }
    """
    Given path ACTION
    When method PUT
    Then status 200

  # GET ---> inventory
    Given path inventoryBase,'/',ID
    When method GET
    Then status 200
    And match $.inventoryId == ID
    And match $.name == 'Los Vegas Inventory'
    And match $.productLines[0].quantity == 20
    And match $.productLines[0].productId == 'PRODUCT 1'
    And match $.links[0].name == 'Verify'
