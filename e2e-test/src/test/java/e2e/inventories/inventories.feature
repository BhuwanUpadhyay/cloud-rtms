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

  # GET ---> task
    * def GET_TASK = $.links[0].path
    * def PROCESS_ID = getTaskInfoByIndex(GET_TASK, 1)
    * def TASK_URI = getTaskInfoByIndex(GET_TASK, 0)
    Given path TASK_URI
    * param processInstanceId = PROCESS_ID
    When method GET
    Then status 200
    And match $[0].name == 'DataEntry'
    And match $[0].processInstanceId == PROCESS_ID