package quebec.virtualite.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SortedList<T extends Comparable<T>> extends ArrayList<T>
{
    @Override
    public boolean add(T t)
    {
        if (!super.add(t))
            return false;

        Collections.sort(this);

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        if (!super.addAll(c))
            return false;

        Collections.sort(this);

        return true;
    }
}
