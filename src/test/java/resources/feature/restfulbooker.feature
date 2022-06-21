Feature: Testing different request on the restful booker app

  Scenario: Check if the restful booker app can be accessed by users
    When User sends a GET request to list endpoint
    Then User should get back a valid status code 200

  Scenario: Check if user can create id in booker list
    When User sends a POST request to create booker endpoint
    Then User must get back a validable status code 201

  Scenario: User verify if created id added to restful booker app
    When User sends a GET request to get new booker endpoint
    Then User successfully added id in product list and status code is 200

  Scenario: User verify if created id Update to the restful booker app
    When User sends a PATCH request to update booker endpoint
    Then User should update single data successfully with status code 200

  Scenario: User verify if the created id is Delete from the application
    When User sends a Delete request to delete endpoint
    Then User should delete category id from application successfully with status code 200
    And User check delete should not more exist in category list
    And User check status code should be 200







