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
    public boolean equals(Object thatObject)
    {
        if (!(thatObject instanceof ViewObject))
            return false;

        ViewObject that = (ViewObject) thatObject;

        return (this.id == that.id)
               && (this.text.equals(that.text));
    }

    @Override
    public String toString()
    {
        return format("ViewObject[id=%d,text=%s]", id, text);
    }
}
