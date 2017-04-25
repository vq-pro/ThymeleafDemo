package quebec.virtualite.thymeleafdemo.ui.view;

import quebec.virtualite.utils.ui.view.BasePageObject;

public abstract class PageObject extends BasePageObject
{
    public String viewName()
    {
        return elementValue("VIEW_NAME");
    }
}
