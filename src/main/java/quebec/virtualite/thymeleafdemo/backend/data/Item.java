package quebec.virtualite.thymeleafdemo.backend.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    protected Item()
    {
    }

    public Item(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Item(String name)
    {
        this(0, name);
    }

    @Override
    public boolean equals(Object thatObject)
    {
        if (!(thatObject instanceof Item))
            return false;

        Item that = (Item) thatObject;

        if (this.id != 0 && that.id != 0 && this.id != that.id)
            return false;

        return (this.name == null)
               ? that.name == null
               : this.name.equals(that.name);
    }

    @Override
    public String toString()
    {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    public long getId()
    {
        return id;
    }

    public Item setId(long id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Item setName(String name)
    {
        this.name = name;
        return this;
    }
}
