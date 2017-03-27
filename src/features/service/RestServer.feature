Feature: Rest Server

  Scenario: Add an item

    When we call to add an item
    Then the item has been added to the DB

  Scenario: Delete an item

    Given an item in the DB
    When we call to delete the item
    Then the item is gone from the DB

  Scenario: Delete an item not found

    When we call to delete an item not in the database
    Then we get the exception "ItemNotFoundException"

  Scenario: Get a single item

    Given an item in the DB
    When we request that item
    Then we get the same item

  Scenario: Get an item not found

    When we request an item not in the database
    Then we get the exception "ItemNotFoundException"

  Scenario: Get a list of items

    Given some items in the DB
    When we request the items
    Then we get an array with the same items

  Scenario: Update an item

    Given an item in the DB
    When we call to update the item
    Then the item has been updated in the DB
