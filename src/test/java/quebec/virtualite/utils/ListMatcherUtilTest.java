package quebec.virtualite.utils;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static quebec.virtualite.utils.backend.EntityUtils.sameTypes;
import static quebec.virtualite.utils.backend.EntityUtils.sameValues;

public class ListMatcherUtilTest
{
    @Test
    public void isList() throws Exception
    {
        List<MyObject> expectedList = asList(new MyObject(1), new MyObject(2));
        List<MyObject> actualList = asList(new MyObject(1));

        try
        {
            assertThat(actualList,
                ListMatcherUtil.isList(expectedList));
        }
        catch(AssertionError e)
        {
            assertThat(e.getMessage(),
                is("\nExpected: <[<[1, 2]>]>\n     but: was <[1]>"));
        }
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