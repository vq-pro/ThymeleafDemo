package quebec.virtualite.daily.backend;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import quebec.virtualite.daily.Application;
import quebec.virtualite.daily.backend.services.RestClient;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class RestServerErrorsSteps
{
    @Autowired
    private RestClient restClient;

    private Exception exception;

    @When("^we call to delete an item not in the database$")
    public void weCallToDeleteAnItemNotInTheDatabase() throws Throwable
    {
        try
        {
            restClient.deleteItem(-1);
        }
        catch (Exception e)
        {
            exception = e;
        }
    }

    @Then("^we get the exception \"([^\"]*)\"$")
    public void weGetTheException(String expectedExceptionClassName) throws Throwable
    {
        assertThat(exception.getClass().getSimpleName(),
            is(expectedExceptionClassName));
    }

    @When("^we request an item not in the database$")
    public void weRequestAnItemNotInTheDatabase() throws Throwable
    {
        try
        {
            restClient.getItem(-1);
        }
        catch (Exception e)
        {
            exception = e;
        }
    }
}
