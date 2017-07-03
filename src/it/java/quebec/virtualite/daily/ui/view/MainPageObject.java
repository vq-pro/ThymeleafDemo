package quebec.virtualite.daily.ui.view;

import org.springframework.stereotype.Component;
import quebec.virtualite.daily.backend.data.Item;

import java.io.IOException;
import java.util.List;

import static quebec.virtualite.daily.ui.Messages.MSG_BUTTON_ADD;
import static quebec.virtualite.daily.ui.Messages.MSG_BUTTON_DELETE;
import static quebec.virtualite.daily.ui.Messages.MSG_NO_ITEMS;
import static quebec.virtualite.daily.ui.Messages.MSG_PROMPT_ADD_ITEM;
import static quebec.virtualite.daily.ui.Messages.MSG_TITLE;
import static quebec.virtualite.daily.ui.view.MainView.ID_BUTTON_ADD;
import static quebec.virtualite.daily.ui.view.MainView.ID_BUTTON_DELETE;
import static quebec.virtualite.daily.ui.view.MainView.ID_ITEM;
import static quebec.virtualite.daily.ui.view.MainView.ID_NO_ITEMS_MSG;
import static quebec.virtualite.daily.ui.view.MainView.URL_MAINVIEW;
import static quebec.virtualite.daily.ui.view.MainView.VIEW_NAME;
import static quebec.virtualite.utils.ui.view.PopupPrompt.promptAndAccept;
import static quebec.virtualite.utils.ui.view.PopupPrompt.promptAndCancel;

@Component
public class MainPageObject extends PageObject
{
    public void addItemAndConfirm(String newItemName) throws IOException
    {
        clickWithPopups(ID_BUTTON_ADD,
            promptAndAccept(message(MSG_PROMPT_ADD_ITEM), newItemName));
    }

    public void addItemButCancel() throws IOException
    {
        clickWithPopups(ID_BUTTON_ADD,
            promptAndCancel(message(MSG_PROMPT_ADD_ITEM)));
    }

    public boolean canAddItems()
    {
        return hasButton(ID_BUTTON_ADD, MSG_BUTTON_ADD);
    }

    public boolean canDeleteItem(Item item)
    {
        return hasButton(idWithParam(ID_BUTTON_DELETE, item.getId()), MSG_BUTTON_DELETE);
    }

    public void deleteItem(Item item) throws IOException
    {
        click(idWithParam(ID_BUTTON_DELETE, item.getId()));
    }

    public List<String> getItems()
    {
        return elementsText(ID_ITEM);
    }

    public void go() throws IOException
    {
        go(URL_MAINVIEW);
    }

    public boolean isViewingMain()
    {
        return elementText("VIEW_NAME").equals(VIEW_NAME)
               && title().equals(message(MSG_TITLE));
    }

    public boolean noItemsMessage()
    {
        return exists(ID_NO_ITEMS_MSG)
               && elementText(ID_NO_ITEMS_MSG).equals(message(MSG_NO_ITEMS));
    }
}
