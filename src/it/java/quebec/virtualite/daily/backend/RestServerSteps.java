package quebec.virtualite.daily.backend;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import quebec.virtualite.daily.Application;
import quebec.virtualite.daily.backend.data.Database;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.daily.backend.services.RestClient;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class RestServerSteps
{
    private static final String UPDATED_TEXT = "AAA";

    private final Item itemA = new Item("A");
    private final Item itemB = new Item("B");
    private final Item itemC = new Item("C");

    @Autowired
    private Database db;

    @Autowired
    private RestClient restClient;

    private Item responseItem;
    private Item[] responseItems;

    @Before
    public void _init() throws Throwable
    {
        db.deleteAllItems();
    }

    @Given("^an item in the DB$")
    public void anItemInTheDB() throws Throwable
    {
        db.save(itemA);

        assertThat(db.getItem(itemA.getId()), is(itemA));
    }

    @Given("^some items in the DB$")
    public void someItemsInTheDB() throws Throwable
    {
        db.save(itemB, itemA, itemC);
    }

    @Then("^the item has been added to the DB$")
    public void theItemHasBeenAddedToTheDB() throws Throwable
    {
        assertThat(db.getItem(responseItem.getId()), is(itemA));
    }

    @Then("^the item has been updated in the DB$")
    public void theItemHasBeenUpdatedInTheDB() throws Throwable
    {
        Item updatedItem = db.getItem(itemA.getId());

        assertThat(updatedItem, not(nullValue()));
        assertThat(updatedItem.getName(), is(UPDATED_TEXT));
    }

    @Then("^the item is gone from the DB$")
    public void theItemIsGoneFromTheDB() throws Throwable
    {
        assertThat(db.getItem(itemA.getId()), is(nullValue()));
    }

    @When("^we call to add an item$")
    public void weCallToAddAnItem() throws Throwable
    {
        responseItem = restClient.addItem(itemA);
    }

    @When("^we call to delete the item$")
    public void weCallToDeleteTheItem() throws Throwable
    {
        restClient.deleteItem(itemA.getId());
    }

    @When("^we call to update the item$")
    public void weCallToUpdateTheItem() throws Throwable
    {
        itemA.setName(UPDATED_TEXT);

        restClient.updateItem(itemA);
    }

    @Then("^we get an array with the same items$")
    public void weGetAnArrayWithTheSameItems() throws Throwable
    {
        assertThat(responseItems, is(new Item[]
            {
                itemA,
                itemB,
                itemC
            }));
    }

    @Then("^we get the same item$")
    public void weGetTheSameItem() throws Throwable
    {
        assertThat(responseItem, is(itemA));
    }

    @When("^we request that item$")
    public void weRequestThatItem() throws Throwable
    {
        responseItem = restClient.getItem(itemA.getId());
    }

    @When("^we request the items$")
    public void weRequestTheItems() throws Throwable
    {
        responseItems = restClient.getItems();
    }
}
