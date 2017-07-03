package quebec.virtualite.daily.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.daily.backend.ItemNotFoundException;
import quebec.virtualite.daily.backend.data.Database;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.daily.backend.services.RestFunctions;

import javax.transaction.Transactional;

@RestController
@Transactional
public class RestServerImpl implements RestFunctions, RestURLs
{
    private static final String ID_ITEM = "/{id}";

    @Autowired
    private Database db;

    @PostMapping(URL_ADD_ITEM)
    public Item addItem(@RequestBody Item item)
    {
        return db.save(item);
    }

    @DeleteMapping(URL_DELETE_ITEM + ID_ITEM)
    public void deleteItem(@PathVariable long id) throws ItemNotFoundException
    {
        validateItem(id);

        db.deleteItem(id);
    }

    @GetMapping(URL_GET_ITEM + ID_ITEM)
    public Item getItem(@PathVariable long id) throws ItemNotFoundException
    {
        return validateItem(id);
    }

    @GetMapping(URL_GET_ITEMS)
    public Item[] getItems()
    {
        return db.getItems()
            .toArray(new Item[0]);
    }

    @PutMapping(URL_UPDATE_ITEM)
    public void updateItem(@RequestBody Item item)
    {
        db.save(item);
    }

    private Item validateItem(long id) throws ItemNotFoundException
    {
        Item item = db.getItem(id);
        if (item == null)
            throw new ItemNotFoundException();

        return item;
    }
}
