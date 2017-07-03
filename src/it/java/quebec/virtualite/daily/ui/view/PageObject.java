package quebec.virtualite.daily.ui.view;

import quebec.virtualite.utils.ui.view.BasePageObject;

public abstract class PageObject extends BasePageObject
{
    public String viewName()
    {
        return elementValue("VIEW_NAME");
    }
}
