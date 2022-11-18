Feature: Collateral data can be saved to, and retrieved from, the Collaterals service

  @smoke @regression
  Scenario: Valid collateral can be added to the service
    Given the Collaterals service is running
    When I add valid collateral with id '1'
    Then I receive a '200' HTTP status
    And I receive a response body with '1'

  @regression
  Scenario: Attempting to add collateral with a value of less than 1000000 is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with a value of '1000'
    Then I receive a '400' HTTP status

  @regression
  Scenario: Attempting to add collateral with no asset type is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with no asset type
    Then I receive a '400' HTTP status

  @regression
  Scenario: Attempting to add collateral with an asset type which is not 'asset' is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with an asset type of 'bonds'
    Then I receive a '400' HTTP status

  # This result seems odd and should be discussed
  @regression
  Scenario: Adding collateral with no currency specified is accepted
    Given the Collaterals service is running
    When I add otherwise valid collateral with no currency specified
    Then I receive a '200' HTTP status

  @regression
  Scenario: Attempting to add collateral with no year specified is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with no year specified
    Then I receive a '400' HTTP status

  @regression
  Scenario: Attempting to add collateral with a year specified before 2000 is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with a year '1999' specified
    Then I receive a '400' HTTP status

   # This is a scenario that needs to be discussed. The date given to the collateral created is the current time. We would have to manipulate the current time to test it. Unclear why this is important.
   @ignore @regression
  Scenario: Attempting to add collateral with a date created before 1st October 2017 is rejected
    Given the Collaterals service is running
    When I add otherwise valid collateral with a date '1999' specified
    Then I receive a '400' HTTP status

  @smoke @regression
  Scenario Outline: Collateral can be retrieved
    Given the Collaterals service is running
    And I have added collateral with id "<id>", type "<type>", name "<name>", currency "<currency>", year "<year>" and value "<value>"
    When I request collateral with id "<id>"
    Then I receive a '200' HTTP status
    And  I receive a response body with id "<id>", type "<type>", name "<name>", currency "<currency>", year "<year>" and value "<value>"
    Examples:
    | id | type  | name      | currency | year | value   |
    | 1  | asset | myasset   | gbp      | 2019 | 1000000 |
    | 2  | asset | yourasset | usd      | 2008 | 2000000 |
    | 3  | asset | hisasset  | eur      | 2010 | 1100000 |

  @regression
  Scenario: Attempting to retrieve collateral with a an unknown id is rejected
    Given the Collaterals service is running
    When I attempt to retrieve collateral with an unknown id
    Then I receive a '404' HTTP status