package quebec.virtualite.utils.ui.view;

public abstract class Popup
{
    protected final String expectedMessage;
    protected final boolean accept;

    protected Popup(String message, boolean accept)
    {
        this.expectedMessage = message;
        this.accept = accept;
    }

    protected abstract void expect();

    protected abstract void verify();
}
