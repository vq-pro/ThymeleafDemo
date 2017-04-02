package quebec.virtualite.utils.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class DtoConverterIT
{
    private ParentDB inputDB;

    @Before
    public void init()
    {
        // Given
        ChildDB childDB = new ChildDB()
            .setId(3);

        inputDB = new ParentDB()
            .setId(1);

        ParentChildDB parentChildDB = new ParentChildDB()
            .setId(2)
            .setParent(inputDB)
            .setChild(childDB);

        inputDB.setChildren(asList(parentChildDB));
    }

    @Test
    public void build_withList() throws Exception
    {
        // When
        List<Parent> output = DtoConverter.build(Parent.class, asList(inputDB));

        // Then
        assertThat(output.size(), is(1));
        assertThat(output.get(0).getId(), is(inputDB.getId()));
    }

    @Test
    public void build_withNullObject_returnNull() throws Exception
    {
        // When
        Parent output = DtoConverter.build(Parent.class, (Object) null);

        // Then
        assertNull(output);
    }

    @Test
    public void build_withNullList_returnNull() throws Exception
    {
        // When
        List<Parent> output = DtoConverter.build(Parent.class, null);

        // Then
        assertNull(output);
    }

    @Test
    public void build_withObject() throws Exception
    {
        // When
        Parent output = DtoConverter.build(Parent.class, inputDB);

        // Then
        assertThat(output.getId(), is(inputDB.getId()));

        assertThat(output.getChildren().size(), is(inputDB.getChildren().size()));

        assertThat(output.getChildren().get(0).getId(),
            is(inputDB.getChildren().get(0).getId()));

        assertThat(output.getChildren().get(0).getChild().getId(),
            is(inputDB.getChildren().get(0).getChild().getId()));
    }

    @Test(expected = ConverterException.class)
    public void build_whenSameClass_exception() throws Exception
    {
        // When
        DtoConverter.build(Parent.class, new Parent().setId(1));
    }

    private static class Child
    {
        private long id;

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }
    }

    private static class ChildDB
    {
        private long id;

        public long getId()
        {
            return id;
        }

        public ChildDB setId(long id)
        {
            this.id = id;
            return this;
        }
    }

    private static class Parent
    {
        private long id;
        private List<ParentChild> children;

        public List<ParentChild> getChildren()
        {
            return children;
        }

        public Parent setChildren(List<ParentChild> children)
        {
            this.children = children;
            return this;
        }

        public long getId()
        {
            return id;
        }

        public Parent setId(long id)
        {
            this.id = id;
            return this;
        }
    }

    private static class ParentChild
    {
        private long id;
        private Child child;

        public Child getChild()
        {
            return child;
        }

        public ParentChild setChild(Child child)
        {
            this.child = child;
            return this;
        }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }
    }

    private static class ParentChildDB
    {
        private long id;

        @JsonIgnore
        private ParentDB parent;

        private ChildDB child;

        public ChildDB getChild()
        {
            return child;
        }

        public ParentChildDB setChild(ChildDB child)
        {
            this.child = child;
            return this;
        }

        public long getId()
        {
            return id;
        }

        public ParentChildDB setId(long id)
        {
            this.id = id;
            return this;
        }

        public ParentDB getParent()
        {
            return parent;
        }

        public ParentChildDB setParent(ParentDB parent)
        {
            this.parent = parent;
            return this;
        }
    }

    private static class ParentDB
    {
        private long id;
        private List<ParentChildDB> children;

        public List<ParentChildDB> getChildren()
        {
            return children;
        }

        public ParentDB setChildren(List<ParentChildDB> children)
        {
            this.children = children;
            return this;
        }

        public long getId()
        {
            return id;
        }

        public ParentDB setId(long id)
        {
            this.id = id;
            return this;
        }
    }
}
