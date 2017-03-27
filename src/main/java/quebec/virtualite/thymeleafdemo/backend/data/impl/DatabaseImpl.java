package quebec.virtualite.thymeleafdemo.backend.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quebec.virtualite.thymeleafdemo.backend.data.Database;
import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.thymeleafdemo.backend.data.ItemRepository;

import java.util.List;

import static java.util.Arrays.stream;

@Service
public class DatabaseImpl implements Database
{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void deleteAllItems()
    {
        itemRepository.deleteAll();
    }

    @Override
    public void deleteItem(long id)
    {
        itemRepository.delete(id);
    }

    @Override
    public Item getItem(long id)
    {
        return itemRepository.findOne(id);
    }

    @Override
    public List<Item> getItems()
    {
        return itemRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Item save(Item item)
    {
        return itemRepository.save(item);
    }

    @Override
    public void save(Item... items)
    {
        stream(items)
            .forEach(item ->
                itemRepository.save(item));
    }
}
