package quebec.virtualite.utils.ui.view;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PopupAlert extends Popup
{
    private static String actualAlertMessage;
    private static boolean displayAlert;

    private PopupAlert(String message)
    {
        super(message, true);
    }

    public static Popup alert(String message)
    {
        return new PopupAlert(message);
    }

    @Override
    public void expect()
    {
        displayAlert = true;
    }

    @Override
    public void verify()
    {
        assertNotNull("Missing alert('" + expectedMessage + "')",
            actualAlertMessage);
        assertThat("Wrong alert expectedMessage",
            actualAlertMessage, is(expectedMessage));

        actualAlertMessage = null;
    }

    static void alertHandler(String message)
    {
        assertTrue("Not expecting 'alert(" + message + ")'", displayAlert);
        displayAlert = false;

        actualAlertMessage = message;
    }
}
