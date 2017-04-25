package quebec.virtualite.utils.ui.view;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PopupConfirm extends Popup
{
    private static String actualConfirmationMessage;
    private static boolean displayConfirmation;
    private static boolean resultConfirmation;

    private PopupConfirm(String message, boolean accept)
    {
        super(message, accept);
    }

    public static Popup confirmAndAccept(String message)
    {
        return new PopupConfirm(message, true);
    }

    public static Popup confirmAndCancel(String message)
    {
        return new PopupConfirm(message, false);
    }

    @Override
    public void expect()
    {
        displayConfirmation = true;
        resultConfirmation = accept;
    }

    @Override
    public void verify()
    {
        assertNotNull("Missing confirm('" + expectedMessage + "')",
            actualConfirmationMessage);
        assertThat("Wrong confirm expectedMessage",
            actualConfirmationMessage, is(expectedMessage));

        actualConfirmationMessage = null;
    }

    static boolean confirmHandler(String message)
    {
        assertTrue("Not expecting 'confirm(" + message + ")'", displayConfirmation);
        displayConfirmation = false;

        actualConfirmationMessage = message;
        return resultConfirmation;
    }
}
