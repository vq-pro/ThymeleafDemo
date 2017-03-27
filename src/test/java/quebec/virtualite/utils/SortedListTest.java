package quebec.virtualite.utils;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static quebec.virtualite.utils.ListMatcherUtil.isList;

public class SortedListTest
{
    private List<Element> list = new SortedList<>();
    private Element element1 = new Element(1);
    private Element element2 = new Element(2);
    private Element element3 = new Element(3);

    @Test
    public void add() throws Exception
    {
        // When
        list.add(element2);
        list.add(element1);

        // Then
        assertThat(list, isList(element1, element2));
    }

    @Test
    public void addAll() throws Exception
    {
        // When
        list.addAll(asList(element2, element1));

        // Then
        assertThat(list, isList(element1, element2));
    }

    @Test
    public void sort() throws Exception
    {
        // Given
        assertThat(list, is(empty()));

        list.add(element2);
        list.add(element1);

        assertThat(list.get(0), is(element1));

        // When
        list.add(element3);
        list.remove(element1);

        // Then
        assertThat(list, isList(element2, element3));
    }

    private class Element implements Comparable<Element>
    {
        private Integer value;

        Element(int value)
        {
            this.value = value;
        }

        @Override
        public int compareTo(Element that)
        {
            return this.value.compareTo(that.value);
        }

        public Integer getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return String.valueOf(value);
        }
    }
}