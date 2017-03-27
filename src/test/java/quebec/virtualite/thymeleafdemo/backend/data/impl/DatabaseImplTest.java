package quebec.virtualite.thymeleafdemo.backend.data.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import quebec.virtualite.thymeleafdemo.backend.data.Database;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.thymeleafdemo.backend.data.ItemRepository;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseImplTest
{
    private static final long ID_ITEM = 123;
    private static final String ITEM_NAME = "item";
    private static final String ITEM_NAME2 = "item2";

    @InjectMocks
    private Database db = new DatabaseImpl();

    @Mock
    private ItemRepository mockedItemRepository;

    @Test
    public void deleteAllItems() throws Exception
    {
        db.deleteAllItems();

        // Then
        verify(mockedItemRepository)
            .deleteAll();
    }

    @Test
    public void deleteItem() throws Exception
    {
        // When
        db.deleteItem(ID_ITEM);

        // Then
        verify(mockedItemRepository).delete(ID_ITEM);
    }

    @Test
    public void getItem() throws Exception
    {
        // When
        db.getItem(ID_ITEM);

        // Then
        verify(mockedItemRepository).findOne(ID_ITEM);
    }
    @Test
    public void getItems() throws Exception
    {
        // When
        db.getItems();

        // Then
        verify(mockedItemRepository).findAllByOrderByNameAsc();
    }

    @Test
    public void saveItem() throws Exception
    {
        // Given
        Item item = new Item(ITEM_NAME);

        // When
        db.save(item);

        // Then
        verify(mockedItemRepository).save(item);
    }

    @Test
    public void saveItems() throws Exception
    {
        // Given
        Item item = new Item(ITEM_NAME);
        Item item2 = new Item(ITEM_NAME2);

        // When
        db.save(item, item2);

        // Then
        verify(mockedItemRepository).save(item);
        verify(mockedItemRepository).save(item2);
    }
}
