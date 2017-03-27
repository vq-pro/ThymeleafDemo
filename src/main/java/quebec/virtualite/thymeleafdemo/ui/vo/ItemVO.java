package quebec.virtualite.thymeleafdemo.ui.vo;

import quebec.virtualite.thymeleafdemo.backend.data.Item;
import quebec.virtualite.utils.ui.ViewObject;

public class ItemVO extends ViewObject
{
    public ItemVO(Item item)
    {
        super(item.getId(), item.getName());
    }
}
