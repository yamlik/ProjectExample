@UI
Feature: Verify all the regression scenarios for Reference Maintenance feature

  @TESTAUTO-102 @TSNT-5515 @productUnderTest
  Scenario:TC-58879 - productUnderTest - Rules - Generate Template for Reference Maintenance, load data and verify Table Status View (XLSX)
    Given I am on "Reference Maintenance" Page
    When I add a new reference table table "XlsxTable"
    And I search Reference Maintenance table with table name of "XlsxTable"
    And I click on change of the reference table named "XlsxTable"
    And I add a filed on the new table with data "DataForZIP"
    And I add a filed on the new table with data "DataForCardNo"
    And I add a filed on the new table with data "DataForName"
    And I save the reference Table
    Then I search Reference Maintenance table with table name of "XlsxTable"
    And I generate template of table named "XlsxTable"
    Then I verify header in generated template of XLS or XLSX file is correct with given list "ListOfHeaders"
    Given I transfer file "R80TESTAUTO102.xlsx" to batch service input directory
    Given I am on "Reference Maintenance" Page
    Then I verify reference table named "XlsxTable" updated correctly in table status

  @TESTAUTO-102 @TSNT-6283 @productUnderTest
  Scenario:TC-58879 - productUnderTest - Rules - Generate Template for Reference Maintenance, load data and verify Table Status View (XLS)
    Given I am on "Reference Maintenance" Page
    When I add a new reference table table "XlsTable"
    And I search Reference Maintenance table with table name of "XlsTable"
    And I click on change of the reference table named "XlsTable"
    And I add a filed on the new table with data "DataForZIP"
    And I add a filed on the new table with data "DataForCardNo"
    And I add a filed on the new table with data "DataForName"
    And I save the reference Table
    Then I search Reference Maintenance table with table name of "XlsTable"
    And I generate template of table named "XlsTable"
    Then I verify header in generated template of XLS or XLSX file is correct with given list "ListOfHeaders"
    Given I transfer file "R81TESTAUTO102.xls" to batch service input directory
    Given I am on "Reference Maintenance" Page
    Then I verify reference table named "XlsTable" updated correctly in table status

  @TESTAUTO-102 @TSNT-6284 @productUnderTest
  Scenario:TC-58879 - productUnderTest - Rules - Generate Template for Reference Maintenance, load data and verify Table Status View (CSV)
    Given I am on "Reference Maintenance" Page
    When I add a new reference table table "CsvTable"
    And I search Reference Maintenance table with table name of "CsvTable"
    And I click on change of the reference table named "CsvTable"
    And I add a filed on the new table with data "DataForZIP"
    And I add a filed on the new table with data "DataForCardNo"
    And I add a filed on the new table with data "DataForName"
    And I save the reference Table
    Then I search Reference Maintenance table with table name of "CsvTable"
    And I generate template of table named "CsvTable"
    Then I verify header in generated template of TXT or CSV file is correct with given list "ListOfHeaders"
    Given I transfer file "R82TESTAUTO102.csv" to batch service input directory
    Given I am on "Reference Maintenance" Page
    Then I verify reference table named "CsvTable" updated correctly in table status

  @TESTAUTO-102 @TSNT-6285 @productUnderTest
  Scenario:TC-58879 - productUnderTest - Rules - Generate Template for Reference Maintenance, load data and verify Table Status View (TXT)
    Given I am on "Reference Maintenance" Page
    When I add a new reference table table "TxtTable"
    And I search Reference Maintenance table with table name of "TxtTable"
    And I click on change of the reference table named "TxtTable"
    And I add a filed on the new table with data "DataForZIP"
    And I add a filed on the new table with data "DataForCardNo"
    And I add a filed on the new table with data "DataForName"
    And I save the reference Table
    Then I search Reference Maintenance table with table name of "TxtTable"
    And I generate template of table named "TxtTable"
    Then I verify header in generated template of TXT or CSV file is correct with given list "ListOfHeaders"
    Given I transfer file "R83TESTAUTO102.txt" to batch service input directory
    Given I am on "Reference Maintenance" Page
    Then I verify reference table named "TxtTable" updated correctly in table status

