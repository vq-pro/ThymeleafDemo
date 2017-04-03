package quebec.virtualite.utils.ui.vo;

import static java.lang.String.format;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

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
    public int hashCode()
    {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that)
    {
        return reflectionEquals(this, that);
    }

    @Override
    public String toString()
    {
        return format("(%d, %s)", id, text);
    }
}
