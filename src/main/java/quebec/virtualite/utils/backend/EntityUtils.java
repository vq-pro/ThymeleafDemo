package quebec.virtualite.utils.backend;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class EntityUtils
{
    public static boolean sameIDs(long entityId, long otherId)
    {
        return (entityId == 0 || otherId == 0 || entityId == otherId);
    }

    public static <T> boolean sameLists(List<T> entityList, List<T> otherList)
    {
        if (entityList == null)
            return (otherList == null);

        if (otherList == null)
            return (entityList == null);

        if (entityList.size() != otherList.size())
            return false;

        for (int i = 0; i < entityList.size(); i++)
        {
            if (!entityList.get(i).equals(otherList.get(i)))
                return false;
        }

        return true;
    }

    public static boolean sameTypes(Object entity, Object otherObject)
    {
        if (otherObject == null)
        {
            // entity (this) can never be null. So in this case, they are not equal.
            return false;
        }

        return entity.getClass().equals(otherObject.getClass());
    }

    public static <T> boolean sameValues(T entityProperty, T otherProperty)
    {
        return (entityProperty == null)
               ? otherProperty == null
               : entityProperty.equals(otherProperty);
    }

    public static <T> String toString(T entity)
    {
        return ToStringBuilder.reflectionToString(entity, SHORT_PREFIX_STYLE);
    }
}
