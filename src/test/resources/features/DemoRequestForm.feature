Feature: Fill demo request details

  Scenario: Fill up the details using the user details given here

    Given Open the Access page and click on the request
    When Fill the required detail with name "Sugumar" email Id "sugumar@gmail.com" phoneNo 123456 company nams "Gallup" and postancode 12249
    And Select the job title as "Executive"
    And Select the country as "Germany"
    And Select the personal reason as "CliftonStrengths Team Reporting and Visualizations"
    Then Click on the accknlogement

