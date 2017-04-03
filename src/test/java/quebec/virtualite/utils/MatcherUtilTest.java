package quebec.virtualite.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static quebec.virtualite.utils.backend.EntityUtils.sameTypes;
import static quebec.virtualite.utils.backend.EntityUtils.sameValues;

public class MatcherUtilTest
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isArrayAsStrings_wrongContent() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[\"1\", \"3\"]>\n     but: was [<1>, <2>]");

        assertThat(new MyObject[]{
                new MyObject(1),
                new MyObject(2)},
            MatcherUtil.hasToStringArray("1", "3"));
    }

    @Test
    public void isArrayAsStrings_wrongLength() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[\"1\", \"2\"]>\n     but: was [<1>]");

        assertThat(new MyObject[]{new MyObject(1)},
            MatcherUtil.hasToStringArray("1", "2"));
    }

    @Test
    public void isListAsStrings_wrongContent() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[\"1\", \"3\"]>\n     but: was <[1, 2]>");

        assertThat(asList(new MyObject(1), new MyObject(2)),
            MatcherUtil.hasToStringList("1", "3"));
    }

    @Test
    public void isListAsStrings_wrongLength() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[\"1\", \"2\"]>\n     but: was <[1]>");

        assertThat(asList(new MyObject(1)),
            MatcherUtil.hasToStringList("1", "2"));
    }

    @Test
    public void isList_wrongContent() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[<[1, 3]>]>\n     but: was <[1, 2]>");

        assertThat(asList(new MyObject(1), new MyObject(2)),
            MatcherUtil.isList(asList(new MyObject(1), new MyObject(3))));
    }

    @Test
    public void isList_wrongLength() throws Exception
    {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(
            "\nExpected: <[<[1, 2]>]>\n     but: was <[1]>");

        assertThat(asList(new MyObject(1)),
            MatcherUtil.isList(asList(new MyObject(1), new MyObject(2))));
    }

    private class MyObject
    {
        private final int value;

        public MyObject(int value)
        {
            this.value = value;
        }

        @Override
        public boolean equals(Object thatObject)
        {
            if (!sameTypes(this, thatObject))
                return false;

            MyObject that = (MyObject) thatObject;

            return sameValues(this.value, that.value);
        }

        @Override
        public String toString()
        {
            return String.valueOf(value);
        }
    }
}