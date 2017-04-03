package quebec.virtualite.utils.ui.vo;

import static java.lang.String.format;

public class ViewObject
{
    public final long id;
    public final String text;

    public ViewObject(long id, String text)
    {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString()
    {
        return format("(%d, %s)", id, text);
    }
}
