package quebec.virtualite.thymeleafdemo.backend.services;

import quebec.virtualite.thymeleafdemo.backend.ItemNotFoundException;
import quebec.virtualite.thymeleafdemo.backend.data.Item;

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
