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
import quebec.virtualite.utils.ui.AbstractHtmlTestHelperSteps;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static quebec.virtualite.thymeleafdemo.ui.Messages.MSG_TITLE;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.ID_BUTTON_ADD;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.ID_BUTTON_DELETE;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.ID_ITEM;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.ID_NO_ITEMS_MSG;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.URL_MAINVIEW;
import static quebec.virtualite.thymeleafdemo.ui.view.MainView.VIEW_NAME;
import static quebec.virtualite.utils.ListMatcherUtil.isList;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class UISteps extends AbstractHtmlTestHelperSteps
{
    private static final String NEW_ITEM_NAME = "New Item";
    private static final String PROMPT_ADD_ITEM = "Enter the name of the item to add:";

    private final Item itemA = new Item("A");
    private final Item itemB = new Item("B");
    private final Item itemC = new Item("C");

    @Autowired
    private Database db;

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

    @Override
    public void shown(String id)
    {
        super.shown(id);

        String idButtonDeleteA = ID_BUTTON_DELETE + String.valueOf(itemA.getId());
        String idButtonDeleteB = ID_BUTTON_DELETE + String.valueOf(itemB.getId());
        String idButtonDeleteC = ID_BUTTON_DELETE + String.valueOf(itemC.getId());

        if (ID_BUTTON_ADD.equals(id))
        {
            assertThat(elementText(id), is("Add"));
        }
        else if (idButtonDeleteA.equals(id)
                 || idButtonDeleteB.equals(id)
                 || idButtonDeleteC.equals(id))
        {
            assertThat(elementText(id), is("X"));
        }
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
        go(URL_MAINVIEW);

        assertThat(elementText("VIEW_NAME"),
            is(VIEW_NAME));

        assertThat("Page was not found: " + URL_MAINVIEW,
            title(), is(message(MSG_TITLE)));

        shown(ID_BUTTON_ADD);
    }

    @Then("^we add an item and cancel$")
    public void weAddAnItemAndCancel() throws Throwable
    {
        expectPromptCancel();
        click(ID_BUTTON_ADD);

        verifyPrompt(PROMPT_ADD_ITEM);
    }

    @Then("^we add an item and confirm$")
    public void weAddAnItemAndConfirm() throws Throwable
    {
        expectPromptOK(NEW_ITEM_NAME);
        click(ID_BUTTON_ADD);

        verifyPrompt(PROMPT_ADD_ITEM);
    }

    @Then("^we add an item with no name$")
    public void weAddAnItemWithNoName() throws Throwable
    {
        expectPromptOK("  ");
        click(ID_BUTTON_ADD);

        verifyPrompt(PROMPT_ADD_ITEM);
    }

    @Then("^we delete an item$")
    public void weDeleteAnItem() throws Throwable
    {
        click(idWithParam(ID_BUTTON_DELETE, itemB.getId()));
    }

    @Then("^we see that the item is no longer in the list$")
    public void weSeeThatTheItemIsNoLongerInTheList() throws Throwable
    {
        assertThat(elementsText(ID_ITEM),
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
        assertThat(elementsText(ID_ITEM),
            isList("A", "B", "C"));

        shown(ID_BUTTON_DELETE + itemA.getId());
        shown(ID_BUTTON_DELETE + itemB.getId());
        shown(ID_BUTTON_DELETE + itemC.getId());
    }

    @Then("^we see the new item in the list$")
    public void weSeeTheNewItemInTheList() throws Throwable
    {
        assertThat(elementsText(ID_ITEM),
            hasItem(NEW_ITEM_NAME));
    }

    @Then("^we see the no items message$")
    public void weSeeTheNoItemsMessage() throws Throwable
    {
        shown(ID_NO_ITEMS_MSG);
    }
}
