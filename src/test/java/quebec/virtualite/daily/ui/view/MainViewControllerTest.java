package quebec.virtualite.daily.ui.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import quebec.virtualite.daily.backend.data.Item;
import quebec.virtualite.daily.backend.services.RestClient;
import quebec.virtualite.utils.ui.vo.ViewObject;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static quebec.virtualite.daily.ui.view.MainView.BEAN;
import static quebec.virtualite.daily.ui.view.MainView.PROP_ITEMS;
import static quebec.virtualite.daily.ui.view.MainView.VIEW_NAME;
import static quebec.virtualite.utils.MatcherUtil.hasToStringArray;
import static quebec.virtualite.utils.ui.ViewControllerUtil.urlEncode;

@RunWith(MockitoJUnitRunner.class)
public class MainViewControllerTest
{
    private static final long ID_ITEM_A = 1;
    private static final long ID_ITEM_B = 2;
    private static final long ID_ITEM_C = 3;
    private static final String ITEM_NAME_A = "A";
    private static final String ITEM_NAME_B = "B";
    private static final String ITEM_NAME_C = "C";
    private static final String NEW_ITEM_NAME = "New Item";

    @InjectMocks
    private quebec.virtualite.daily.ui.view.MainViewController viewController = new quebec.virtualite.daily.ui.view.MainViewController();

    @Mock
    private RestClient mockedRestClient;

    private ModelMap outputModel;
    private String outputViewName;

    @Before
    public void _init() throws Exception
    {
        outputModel = new ModelMap();

        Item[] items =
            {
                new Item(ID_ITEM_A, ITEM_NAME_A),
                new Item(ID_ITEM_B, ITEM_NAME_B),
                new Item(ID_ITEM_C, ITEM_NAME_C)
            };

        when(mockedRestClient.getItems())
            .thenReturn(items);
    }

    @Test
    public void addItem() throws Exception
    {
        // When
        viewController.addItem(urlEncode(NEW_ITEM_NAME), outputModel);

        // Then
        verify(mockedRestClient)
            .addItem(new Item(NEW_ITEM_NAME));
    }

    @Test
    public void addItem_whenNoName_nothingHappens() throws Exception
    {
        // When
        viewController.addItem("   ", outputModel);

        // Then
        verify(mockedRestClient, never())
            .addItem(any());
    }

    @Test
    public void deleteItem() throws Exception
    {
        // When
        viewController.deleteItem(ID_ITEM_A, outputModel);

        // Then
        verify(mockedRestClient)
            .deleteItem(ID_ITEM_A);
    }

    @Test
    public void enterView() throws Exception
    {
        // When
        outputViewName = viewController.enterView(outputModel);

        // Then
        assertThat(outputViewName, is(VIEW_NAME));
        assertThat(outputModel.get(BEAN), is(new MainView()));

        assertThat((ViewObject[]) outputModel.get(PROP_ITEMS),
            hasToStringArray(
                format("(%d, %s)", ID_ITEM_A, ITEM_NAME_A),
                format("(%d, %s)", ID_ITEM_B, ITEM_NAME_B),
                format("(%d, %s)", ID_ITEM_C, ITEM_NAME_C)));
    }
}