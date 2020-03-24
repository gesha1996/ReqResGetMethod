Feature: Reqres Api Testing

  Scenario: validation of single user not found

    Given provided url get request and status code is 404
    When response content-type is "application/json"
    Then match the response is empty body

