package quebec.virtualite.daily.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quebec.virtualite.daily.backend.ItemNotFoundException;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.daily.backend.services.RestClient;
import quebec.virtualite.utils.backend.RestClientUtil;
import quebec.virtualite.utils.backend.ServerException;

import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_ADD_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_DELETE_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_GET_ITEM;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_GET_ITEMS;
import static quebec.virtualite.daily.backend.services.impl.RestURLs.URL_UPDATE_ITEM;

@Service
class RestClientImpl implements RestClient
{
    @Autowired
    private RestClientUtil rest;

    @Override
    public Item addItem(Item item)
    {
        return rest.post(item, Item.class, URL_ADD_ITEM);
    }

    @Override
    public void deleteItem(long id) throws ItemNotFoundException
    {
        try
        {
            rest.doWithErrorHandling(() ->
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
                rest.get(Item.class, URL_GET_ITEM, String.valueOf(id)));
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
        return rest.get(Item[].class, URL_GET_ITEMS);
    }

    @Override
    public void updateItem(Item item)
    {
        rest.put(item, URL_UPDATE_ITEM);
    }

    private void handleItemNotFoundException(ServerException e) throws ItemNotFoundException
    {
        if (e.getMessage().equals(ItemNotFoundException.class.getSimpleName()))
            throw new ItemNotFoundException();
    }
}
