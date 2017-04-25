package quebec.virtualite.utils.ui.view;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PopupPrompt extends Popup
{
    private static String actualPromptMessage;
    private static boolean displayPrompt;
    private static String resultPrompt;

    private final String result;

    private PopupPrompt(String message)
    {
        super(message, false);

        this.result = null;
    }

    private PopupPrompt(String message, String result)
    {
        super(message, true);

        this.result = result;
    }

    public static Popup promptAndAccept(String message, String result)
    {
        return new PopupPrompt(message, result);
    }

    public static Popup promptAndCancel(String message)
    {
        return new PopupPrompt(message);
    }

    public static String promptHandler(String message)
    {
        assertTrue("Not expecting 'prompt(" + message + ")'", displayPrompt);
        displayPrompt = false;

        actualPromptMessage = message;
        return resultPrompt;
    }

    @Override
    public void expect()
    {
        displayPrompt = true;
        resultPrompt = result;
    }

    @Override
    public void verify()
    {
        assertNotNull("Missing prompt('" + expectedMessage + "', " + result + ")",
            actualPromptMessage);
        assertThat("Wrong prompt message",
            actualPromptMessage, is(expectedMessage));

        actualPromptMessage = null;
    }
}
