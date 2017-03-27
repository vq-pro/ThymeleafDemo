package quebec.virtualite.utils;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class ListMatcherUtil
{
    public static <E> Matcher<Iterable<? extends E>> isList(E... expectedItems)
    {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (E item : expectedItems)
        {
            matchers.add(equalTo(item));
        }

        return new IsEqual(IsEqual.equalTo(expectedItems))
        {
            @Override
            public boolean matches(Object actualValue)
            {
                List<E> actualItems = (List<E>) actualValue;

                if (actualItems.size() != expectedItems.length)
                    return false;

                for (int i = 0; i < expectedItems.length; i++)
                {
                    Matcher<? super E> matcher = matchers.get(i);

                    if (!matcher.matches(actualItems.get(i)))
                        return false;
                }

                return true;
            }
        };
    }
}
