package quebec.virtualite.thymeleafdemo.backend.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import quebec.virtualite.thymeleafdemo.backend.ItemNotFoundException;
import quebec.virtualite.thymeleafdemo.backend.data.Database;
import quebec.virtualite.thymeleafdemo.backend.data.Item;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RestServerImplTest
{
    private static final long ID_ITEM = 123;
    private static final long ID_ITEM2 = 456;
    private static final String ITEM_NAME = "item";
    private static final String ITEM_NAME2 = "item2";

    @InjectMocks
    private RestServerImpl restServer;

    @Mock
    private Database mockedDB;

    @Test
    public void addItem() throws Exception
    {
        // Given
        Item inputItem = new Item(ITEM_NAME);

        doReturn(inputItem)
            .when(mockedDB)
            .save(inputItem);

        // When
        Item outputItem = restServer.addItem(inputItem);

        // Then
        assertThat(outputItem, is(inputItem));
        verify(mockedDB).save(inputItem);
    }

    @Test
    public void deleteItem() throws Exception
    {
        // Given
        doReturn(new Item(ITEM_NAME))
            .when(mockedDB)
            .getItem(ID_ITEM);

        // When
        restServer.deleteItem(ID_ITEM);

        // Then
        verify(mockedDB).deleteItem(ID_ITEM);
    }

    @Test(expected = ItemNotFoundException.class)
    public void deleteItem_whenNotFound_exception() throws Exception
    {
        // Given
        doReturn(null)
            .when(mockedDB)
            .getItem(ID_ITEM);

        // When
        restServer.deleteItem(ID_ITEM);
    }

    @Test
    public void getItem() throws Exception
    {
        // Given
        Item input = new Item(ID_ITEM, ITEM_NAME);

        doReturn(input)
            .when(mockedDB)
            .getItem(ID_ITEM);

        // When
        Item output = restServer.getItem(ID_ITEM);

        // Then
        assertThat(output, is(input));
    }

    @Test
    public void getItems() throws Exception
    {
        // Given
        Item[] inputItems = {
            new Item(ID_ITEM, ITEM_NAME),
            new Item(ID_ITEM2, ITEM_NAME2)};

        doReturn(asList(inputItems))
            .when(mockedDB)
            .getItems();

        // When
        Item[] outputItems = restServer.getItems();

        // Then
        assertThat(outputItems, is(inputItems));
    }

    @Test(expected = ItemNotFoundException.class)
    public void getRecipe_whenNotFound_exception() throws Exception
    {
        restServer.getItem(ID_ITEM);
    }

    @Test
    public void updateItem() throws Exception
    {
        // Given
        Item item = new Item(ITEM_NAME);

        // When
        restServer.updateItem(item);

        // Then
        verify(mockedDB).save(item);
    }
}