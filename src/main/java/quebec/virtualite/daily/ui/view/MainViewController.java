package quebec.virtualite.daily.ui.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import quebec.virtualite.daily.backend.ItemNotFoundException;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.daily.backend.services.RestClient;
import quebec.virtualite.daily.ui.vo.ItemVO;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static quebec.virtualite.daily.ui.view.MainView.BEAN;
import static quebec.virtualite.daily.ui.view.MainView.PROP_ITEMS;
import static quebec.virtualite.daily.ui.view.MainView.URL_ITEM_ADD;
import static quebec.virtualite.daily.ui.view.MainView.URL_ITEM_DELETE;
import static quebec.virtualite.daily.ui.view.MainView.URL_MAINVIEW;
import static quebec.virtualite.daily.ui.view.MainView.VIEW_NAME;
import static quebec.virtualite.utils.ui.ViewControllerUtil.urlDecode;
import static quebec.virtualite.utils.ui.vo.ViewConverter.view;

@Controller
public class MainViewController
{
    @Autowired
    private RestClient restClient;

    @GetMapping(URL_ITEM_ADD + "/{itemName}")
    public String addItem(@PathVariable String itemName, ModelMap model)
    {
        itemName = urlDecode(itemName);

        if (!isEmpty(itemName))
        {
            restClient.addItem(new Item(itemName));
        }

        return enterView(model);
    }

    @GetMapping(URL_ITEM_DELETE + "/{id}")
    public String deleteItem(@PathVariable long id, ModelMap model) throws ItemNotFoundException
    {
        restClient.deleteItem(id);

        return enterView(model);
    }

    @GetMapping(URL_MAINVIEW)
    public String enterView(ModelMap model)
    {
        model.put(BEAN, new MainView());
        model.put(PROP_ITEMS,
            view(restClient.getItems(), ItemVO::new));

        return VIEW_NAME;
    }
}
