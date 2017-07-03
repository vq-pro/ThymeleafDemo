package quebec.virtualite.daily.backend.services;

import quebec.virtualite.daily.backend.ItemNotFoundException;
import quebec.virtualite.daily.backend.data.Item;

public interface RestFunctions
{
    Item addItem(Item item);

    void deleteItem(long id)
        throws ItemNotFoundException;

    Item getItem(long id)
        throws ItemNotFoundException;

    Item[] getItems();

    void updateItem(Item item);
}
