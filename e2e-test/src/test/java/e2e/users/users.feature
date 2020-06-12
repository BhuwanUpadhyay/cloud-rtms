Feature: E2E Test - Inventories

  Background:
    * url baseUrl
    * def userBase = '/users'

  Scenario: Create a inventory

    Given path userBase
    And request { userId: 'developerbhuwan', fullName: 'Bhuwan Prasad Upadhyay' }
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match $.userId != null

#  Scenario: Get inventory we just created
#
#    Given path inventoryBase + '0'
#    When method GET
#    Then status 200
#    And match response == { firstName: 'John', lastName: 'Doe', age: 30 }
