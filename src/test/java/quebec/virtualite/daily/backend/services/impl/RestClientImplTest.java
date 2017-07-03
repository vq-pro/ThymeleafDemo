package quebec.virtualite.daily.backend.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.utils.backend.impl.RestClientUtilImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_ADD_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_DELETE_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_GET_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_GET_ITEMS;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_UPDATE_ITEM;

@RunWith(MockitoJUnitRunner.class)
public class RestClientImplTest
{
    static final long ID_ITEM = 1L;
    static final String ITEM_NAME = "A";

    @InjectMocks
    private RestClientImpl rest;

    @Mock
    private RestClientUtilImpl mockedRestClientUtil;

    @Test
    public void addItem() throws Exception
    {
        // Given
        Item inputItem = new Item("A");

        when(mockedRestClientUtil.post(inputItem, Item.class, URL_ADD_ITEM))
            .thenReturn(inputItem);

        // When
        Item outputItem = rest.addItem(inputItem);

        // Then
        assertThat(outputItem, is(inputItem));
    }

    @Test
    public void deleteItem() throws Exception
    {
        // Given
        doCallRealMethod()
            .when(mockedRestClientUtil)
            .doWithErrorHandling(any());

        // When
        rest.deleteItem(ID_ITEM);

        // Then
        verify(mockedRestClientUtil).doWithErrorHandling(any());
        verify(mockedRestClientUtil)
            .delete(URL_DELETE_ITEM, String.valueOf(ID_ITEM));
    }

    @Test
    public void getItem() throws Exception
    {
        // Given
        doCallRealMethod()
            .when(mockedRestClientUtil)
            .doWithErrorHandling(any());

        // When
        rest.getItem(ID_ITEM);

        // Then
        verify(mockedRestClientUtil).doWithErrorHandling(any());
        verify(mockedRestClientUtil)
            .get(Item.class, URL_GET_ITEM, String.valueOf(ID_ITEM));
    }

    @Test
    public void getItems() throws Exception
    {
        // When
        rest.getItems();

        // Then
        verify(mockedRestClientUtil)
            .get(Item[].class, URL_GET_ITEMS);
    }

    @Test
    public void updateItem() throws Exception
    {
        // Given
        Item item = new Item(ITEM_NAME);

        // When
        rest.updateItem(item);

        // Then
        verify(mockedRestClientUtil)
            .put(item, URL_UPDATE_ITEM);
    }
}