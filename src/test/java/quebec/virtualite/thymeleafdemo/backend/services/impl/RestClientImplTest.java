package quebec.virtualite.thymeleafdemo.backend.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;
import quebec.virtualite.thymeleafdemo.backend.ItemNotFoundException;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.utils.backend.impl.RestClientUtilImpl;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_ADD_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_DELETE_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_GET_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_GET_ITEMS;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_UPDATE_ITEM;

@RunWith(MockitoJUnitRunner.class)
public class RestClientImplTest
{
    private static final long ID_ITEM = 1L;
    private static final String ITEM_NAME = "A";

    @InjectMocks
    private RestClientImpl rest;

    @Mock
    private RestClientUtilImpl mockedRestClientUtil;

    @Test
    public void addItem() throws Exception
    {
        // Given
        Item inputItem = new Item("A");

        when(mockedRestClientUtil.post(URL_ADD_ITEM, inputItem, Item.class))
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

    @Test(expected = ItemNotFoundException.class)
    public void deleteItem_whenNotFound_exception() throws Exception
    {
        // Given
        doCallRealMethod()
            .when(mockedRestClientUtil)
            .doWithErrorHandling(any());

        doThrow(serverError(ItemNotFoundException.class))
            .when(mockedRestClientUtil)
            .delete(URL_DELETE_ITEM, String.valueOf(ID_ITEM));

        // When
        rest.deleteItem(ID_ITEM);
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
            .get(URL_GET_ITEM, String.valueOf(ID_ITEM), Item.class);
    }

    @Test(expected = ItemNotFoundException.class)
    public void getItem_whenNotFound_exception() throws Exception
    {
        // Given
        doCallRealMethod()
            .when(mockedRestClientUtil)
            .doWithErrorHandling(any());

        doThrow(serverError(ItemNotFoundException.class))
            .when(mockedRestClientUtil)
            .get(URL_GET_ITEM, String.valueOf(ID_ITEM), Item.class);

        // When
        rest.getItem(ID_ITEM);
    }

    @Test
    public void getItems() throws Exception
    {
        // When
        rest.getItems();

        // Then
        verify(mockedRestClientUtil).get(URL_GET_ITEMS, Item[].class);
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
            .put(URL_UPDATE_ITEM, item);
    }

    private HttpClientErrorException serverError(Class<? extends Throwable> exceptionClass)
    {
        String restError = "{\"exception\":\"" + exceptionClass.getName() + "\"}";

        return new HttpClientErrorException(
            BAD_REQUEST,
            "400",
            restError.getBytes(),
            Charset.defaultCharset()
        );
    }
}