package quebec.virtualite.daily.ui.vo;

import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.utils.ui.vo.ViewObject;

public class ItemVO extends ViewObject
{
    public ItemVO(Item item)
    {
        super(item.getId(), item.getName());
    }
}
