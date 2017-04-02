package quebec.virtualite.utils;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.object.HasToString;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class MatcherUtil
{
    public static <E> Matcher<E[]> isArrayAsStrings(String... expectedToStrings)
    {
        List<Matcher<? super E>> matchers = new ArrayList<>();
        for (String expectedToString : expectedToStrings)
        {
            matchers.add(HasToString.hasToString(expectedToString));
        }

        return new IsEqual(IsEqual.equalTo(expectedToStrings))
        {
            @Override
            public boolean matches(Object actualValue)
            {
                E[] actualItems = (E[]) actualValue;

                if (actualItems.length != expectedToStrings.length)
                    return false;

                for (int i = 0; i < expectedToStrings.length; i++)
                {
                    Matcher<? super E> matcher = matchers.get(i);

                    if (!matcher.matches(actualItems[i]))
                        return false;
                }

                return true;
            }
        };
    }

    public static <E> Matcher<Iterable<? extends E>> isList(E... expectedItems)
    {
        List<Matcher<? super E>> matchers = new ArrayList<>();
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

    public static <E> Matcher<List<E>> isListAsStrings(String... expectedToStrings)
    {
        List<Matcher<? super E>> matchers = new ArrayList<>();
        for (String expectedToString : expectedToStrings)
        {
            matchers.add(HasToString.hasToString(expectedToString));
        }

        return new IsEqual(IsEqual.equalTo(expectedToStrings))
        {
            @Override
            public boolean matches(Object actualValue)
            {
                List<E> actualItems = (List<E>) actualValue;

                if (actualItems.size() != expectedToStrings.length)
                    return false;

                for (int i = 0; i < expectedToStrings.length; i++)
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
