package quebec.virtualite.daily.ui.view;

import org.springframework.context.annotation.Configuration;
import quebec.virtualite.daily.ui.Messages;

import static quebec.virtualite.daily.Application.BASE_URL;

@Configuration
public class MainView implements Messages
{
    public static final String BEAN = "main";
    public static final String VIEW_NAME = "MainView";

    public static final String ID_BUTTON_ADD = "idButtonAdd";
    public static final String ID_BUTTON_DELETE = "idButtonDelete";
    public static final String ID_ITEM = "idItem";
    public static final String ID_NO_ITEMS_MSG = "idNoItemsMsg";

    public static final String PROP_ITEMS = "items";

    public static final String URL_MAINVIEW = BASE_URL + "/main";
    public static final String URL_ITEM_ADD = BASE_URL + "/main/addItem";
    public static final String URL_ITEM_DELETE = BASE_URL + "/main/deleteItem";

    @Override
    public boolean equals(Object that)
    {
        return that instanceof MainView;
    }
}
