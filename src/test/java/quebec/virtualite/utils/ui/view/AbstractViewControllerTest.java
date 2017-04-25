package quebec.virtualite.utils.ui.view;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractViewControllerTest
{
    protected ModelAndView outputViewModel;
    protected ModelMap outputModel;

    protected void call(ModelAndView viewModel)
    {
        outputViewModel = viewModel;
        outputModel = (ModelMap) viewModel.getModel();
    }
}
