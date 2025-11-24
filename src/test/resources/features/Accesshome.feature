Feature: Access Home Page Testing
  @regression
  Scenario: Open Gallup Access Page and Validate the Page buttons

    Given Open Gallup Access and Click on request a demo
    When Check signIn page is loaded
    And Fill the details
    Then Navigate back to the previous page

