package quebec.virtualite.utils.ui.vo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ViewConverterTest
{
    @Test
    public void viewArray() throws Exception
    {
        // Given
        Entity[] entities = {
            new Entity(1, "A"),
            new Entity(2, "B")};

        // When
        ViewObject[] views = ViewConverter.view(entities, EntityVO::new);

        // Then
        assertThat(views,
            is(new ViewObject[]{
                new ViewObject(1, "A"),
                new ViewObject(2, "B")}));
    }

    @Test
    public void viewArray_whenEmpty() throws Exception
    {
        // When
        ViewObject[] views = ViewConverter.view(new Entity[0], EntityVO::new);

        // Then
        assertThat(views,
            is(emptyArray()));
    }

    @Test
    public void viewArray_whenNull() throws Exception
    {
        // When
        ViewObject[] views = ViewConverter.view((Entity[]) null, EntityVO::new);

        // Then
        assertThat(views,
            is(emptyArray()));
    }

    @Test
    public void viewList() throws Exception
    {
        // Given
        List<Entity> entities = asList(
            new Entity(1, "A"),
            new Entity(2, "B"));

        // When
        ViewObject[] views = ViewConverter.view(entities, EntityVO::new);

        // Then
        assertThat(views,
            is(new ViewObject[]{
                new ViewObject(1, "A"),
                new ViewObject(2, "B")}));
    }

    @Test
    public void viewList_whenEmpty() throws Exception
    {
        // When
        ViewObject[] views = ViewConverter.view(new ArrayList<Entity>(), EntityVO::new);

        // Then
        assertThat(views,
            is(emptyArray()));
    }

    @Test
    public void viewList_whenNull() throws Exception
    {
        // When
        ViewObject[] views = ViewConverter.view((List<Entity>) null, EntityVO::new);

        // Then
        assertThat(views,
            is(emptyArray()));
    }

    @Test
    public void viewSingle() throws Exception
    {
        // Given
        Entity entity = new Entity(1, "A");

        // When
        ViewObject view = ViewConverter.view(entity, EntityVO::new);

        // Then
        assertThat(view,
            is(new ViewObject(1, "A")));
    }

    private class Entity
    {
        private final long id;
        private final String name;

        private Entity(long id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }

    private class EntityVO extends ViewObject
    {
        private EntityVO(Entity entity)
        {
            super(entity.id, entity.name);
        }
    }
}