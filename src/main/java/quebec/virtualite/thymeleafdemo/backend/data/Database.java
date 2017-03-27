package quebec.virtualite.thymeleafdemo.backend.data;

import java.util.List;

public interface Database
{
    void deleteAllItems();

    void deleteItem(long id);

    Item getItem(long id);

    List<Item> getItems();

    Item save (Item item);

    void save(Item... items);
}
