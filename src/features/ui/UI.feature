Feature: ThymeleafDemo

  Scenario Outline: Viewing items

    Given <items>
    When the page is displayed
    Then we see <result>

    Examples:
      | items      | result               |
      | some items | the items            |
      | no items   | the no items message |

  Scenario Outline: Adding an item

    Given some items
    When the page is displayed
    And we <do>
    Then we see <result>

    Examples:
      | do                       | result                      |
      | add an item and confirm  | the new item in the list    |
      | add an item and cancel   | that the items don't change |
      | add an item with no name | that the items don't change |

  Scenario: Deleting an item

    Given some items
    When the page is displayed
    And we delete an item
    Then we see that the item is no longer in the list
