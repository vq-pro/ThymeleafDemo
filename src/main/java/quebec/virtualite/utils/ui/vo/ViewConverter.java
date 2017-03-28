package quebec.virtualite.utils.ui.vo;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.stream;

public class ViewConverter
{
    public static <T> ViewObject[] view(final T[] entities, final Function<T, ViewObject> converter)
    {
        if (entities == null)
        {
            return new ViewObject[0];
        }

        return stream(entities)
            .map(converter)
            .toArray(ViewObject[]::new);
    }

    public static <T> ViewObject[] view(final List<T> entities, final Function<T, ViewObject> converter)
    {
        if (entities == null)
        {
            return new ViewObject[0];
        }

        return entities.stream()
            .map(converter)
            .toArray(ViewObject[]::new);
    }

    public static <T> ViewObject view(final T entity, final Function<T, ViewObject> converter)
    {
        return converter.apply(entity);
    }
}
