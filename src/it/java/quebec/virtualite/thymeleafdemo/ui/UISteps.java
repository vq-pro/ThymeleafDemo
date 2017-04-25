package quebec.virtualite.thymeleafdemo.ui;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import quebec.virtualite.thymeleafdemo.Application;
import quebec.virtualite.thymeleafdemo.backend.data.Database;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.thymeleafdemo.ui.view.MainPageObject;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static quebec.virtualite.utils.MatcherUtil.isList;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class UISteps
{
    private static final String NEW_ITEM_NAME = "New Item";

    private static final String PROMPT_ADD_ITEM = "Enter the name of the item to add:";

    private final Item itemA = new Item("A");

    private final Item itemB = new Item("B");

    private final Item itemC = new Item("C");

    @Autowired
    private Database db;

    @Autowired
    private MainPageObject page;

    @Autowired
    private Messages messages;

    @Before
    public void _init()
    {
        db.deleteAllItems();
    }

    @Given("^no items$")
    public void noItems() throws Throwable
    {
        // Nothing to do here
    }

    @Given("^some items$")
    public void someItems() throws Throwable
    {
        db.deleteAllItems();
        db.save(itemB,
            itemA,
            itemC);
    }

    @When("^the page is displayed$")
    public void thePageIsDisplayed() throws Throwable
    {
        page.go();

        assertTrue(page.isViewingMain());
        assertTrue(page.canAddItems());
    }

    @Then("^we add an item and cancel$")
    public void weAddAnItemAndCancel() throws Throwable
    {
        page.addItemButCancel();
    }

    @Then("^we add an item and confirm$")
    public void weAddAnItemAndConfirm() throws Throwable
    {
        page.addItemAndConfirm(NEW_ITEM_NAME);
    }

    @Then("^we add an item with no name$")
    public void weAddAnItemWithNoName() throws Throwable
    {
        page.addItemAndConfirm("  ");
    }

    @Then("^we delete an item$")
    public void weDeleteAnItem() throws Throwable
    {
        page.deleteItem(itemB);
    }

    @Then("^we see that the item is no longer in the list$")
    public void weSeeThatTheItemIsNoLongerInTheList() throws Throwable
    {
        assertThat(page.getItems(),
            not(hasItem(itemB.getName())));
    }

    @Then("^we see that the items don't change$")
    public void weSeeThatTheItemsDontChange() throws Throwable
    {
        weSeeTheItems();
    }

    @Then("^we see the items$")
    public void weSeeTheItems() throws Throwable
    {
        assertThat(page.getItems(),
            isList("A", "B", "C"));

        assertTrue(page.canDeleteItem(itemA));
        assertTrue(page.canDeleteItem(itemB));
        assertTrue(page.canDeleteItem(itemC));
    }

    @Then("^we see the new item in the list$")
    public void weSeeTheNewItemInTheList() throws Throwable
    {
        assertThat(page.getItems(),
            hasItem(NEW_ITEM_NAME));
    }

    @Then("^we see the no items message$")
    public void weSeeTheNoItemsMessage() throws Throwable
    {
        assertTrue(page.noItemsMessage());
    }
}
