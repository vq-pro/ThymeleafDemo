package quebec.virtualite.thymeleafdemo.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quebec.virtualite.thymeleafdemo.backend.ItemNotFoundException;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.thymeleafdemo.backend.services.RestClient;
import quebec.virtualite.utils.backend.RestClientUtil;
import quebec.virtualite.utils.backend.ServerException;

import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_ADD_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_DELETE_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_GET_ITEM;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_GET_ITEMS;
import static quebec.virtualite.thymeleafdemo.backend.services.impl.RestURLs.URL_UPDATE_ITEM;

@Service
class RestClientImpl implements RestClient
{
    @Autowired
    private RestClientUtil rest;

    @Override
    public Item addItem(Item item)
    {
        return rest.post(URL_ADD_ITEM, item, Item.class);
    }

    @Override
    public void deleteItem(long id) throws ItemNotFoundException
    {
        try
        {
            rest.doWithErrorHandling(
                () ->
                {
                    rest.delete(URL_DELETE_ITEM, String.valueOf(id));
                    return null;
                });
        }
        catch (ServerException e)
        {
            handleItemNotFoundException(e);
        }
    }

    @Override
    public Item getItem(long id) throws ItemNotFoundException
    {
        try
        {
            return rest.doWithErrorHandling(() ->
                rest.get(URL_GET_ITEM, String.valueOf(id), Item.class));
        }
        catch (ServerException e)
        {
            handleItemNotFoundException(e);
            throw e;
        }
    }

    @Override
    public Item[] getItems()
    {
        return rest.get(URL_GET_ITEMS, Item[].class);
    }

    @Override
    public void updateItem(Item item)
    {
        rest.put(URL_UPDATE_ITEM, item);
    }

    private void handleItemNotFoundException(ServerException e) throws ItemNotFoundException
    {
        if (e.getMessage().equals(ItemNotFoundException.class.getSimpleName()))
            throw new ItemNotFoundException();
    }
}
