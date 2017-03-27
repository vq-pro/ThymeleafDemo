package quebec.virtualite.thymeleafdemo.backend.data;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ItemTest
{
    @Test
    public void equals_whenComparingDifferentItems_returnsFalse() throws Exception
    {
        assertThat(new Item("A").equals(new Object()), is(false));
        assertThat(new Item("A").equals(new Item("B")), is(false));
        assertThat(new Item(1, "A").equals(new Item(2, "B")), is(false));
    }

    @Test
    public void equals_whenComparingSameItems_returnsTrue() throws Exception
    {
        assertThat(new Item("A").equals(new Item("A")), is(true));
        assertThat(new Item(1, "A").equals(new Item(1, "A")), is(true));
        assertThat(new Item("A").equals(new Item(0, "A")), is(true));
    }

    @Test
    public void toString_verifyOutput() throws Exception
    {
        // Given
        Item item = new Item()
            .setId(1)
            .setName("A");

        // When
        String output = item.toString();

        // Then
        assertThat(output, is("Item[id=1,name=A]"));
    }
}