package quebec.virtualite.thymeleafdemo.backend.services.impl;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.HttpClientErrorException;
import quebec.virtualite.thymeleafdemo.backend.ItemNotFoundException;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.utils.backend.impl.RestClientUtilImpl;

import java.nio.charset.Charset;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestClientImplTest.ID_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_DELETE_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_GET_ITEM;

public class RestClientImplIT
{
    @InjectMocks
    private RestClientImpl rest;

    @Mock
    private RestClientUtilImpl mockedRestClientUtil;

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