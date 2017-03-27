package quebec.virtualite.utils.ui;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class AbstractHtmlTestHelperSteps implements HtmlTestHelperFunctions
{
    @Autowired
    private HtmlTestHelper htmlTestHelper;

    @Override
    public HtmlPage click(String id) throws IOException
    {
        return htmlTestHelper.click(id);
    }

    @Override
    public String dump()
    {
        return htmlTestHelper.dump();
    }

    @Override
    public DomElement element(String id)
    {
        return htmlTestHelper.element(id);
    }

    @Override
    public String elementText(String id)
    {
        return htmlTestHelper.elementText(id);
    }

    @Override
    public String elementValue(String id)
    {
        return htmlTestHelper.elementValue(id);
    }

    @Override
    public List<DomElement> elements(String id)
    {
        return htmlTestHelper.elements(id);
    }

    @Override
    public List<String> elementsText(String id)
    {
        return htmlTestHelper.elementsText(id);
    }

    @Override
    public void emptyList(String idList)
    {
        htmlTestHelper.emptyList(idList);
    }

    @Override
    public boolean exists(String id)
    {
        return htmlTestHelper.exists(id);
    }

    @Override
    public void expectAlert()
    {
        htmlTestHelper.expectAlert();
    }

    @Override
    public void expectPromptCancel()
    {
        htmlTestHelper.expectPromptCancel();
    }

    @Override
    public void expectPromptOK(String result)
    {
        htmlTestHelper.expectPromptOK(result);
    }

    @Override
    public void verifyPrompt(String expectedMessage)
    {
        htmlTestHelper.verifyPrompt(expectedMessage);
    }

    @Override
    public void expectConfirmCancel()
    {
        htmlTestHelper.expectConfirmCancel();
    }

    @Override
    public void expectConfirmOK()
    {
        htmlTestHelper.expectConfirmOK();
    }

    @Override
    public HtmlPage go(String url) throws IOException
    {
        return htmlTestHelper.go(url);
    }

    @Override
    public HtmlPage go(String url, long param) throws IOException
    {
        return htmlTestHelper.go(url, param);
    }

    @Override
    public HtmlPage go(String url, String param) throws IOException
    {
        return htmlTestHelper.go(url, param);
    }

    @Override
    public void hidden(String id)
    {
        htmlTestHelper.hidden(id);
    }

    @Override
    public String message(String key)
    {
        return htmlTestHelper.message(key);
    }

    @Override
    public void onView(String viewName)
    {
        htmlTestHelper.onView(viewName);
    }

    @Override
    public DomNodeList<HtmlElement> options(String idSelect)
    {
        return htmlTestHelper.options(idSelect);
    }

    @Override
    public List<String> optionsText(String idSelect)
    {
        return htmlTestHelper.optionsText(idSelect);
    }

    @Override
    public HtmlPage selectOption(String idSelect, long idOption) throws IOException
    {
        return htmlTestHelper.selectOption(idSelect, idOption);
    }

    @Override
    public void shown(String id)
    {
        htmlTestHelper.shown(id);
    }

    @Override
    public String title()
    {
        return htmlTestHelper.title();
    }

    @Override
    public void verifyAlert(String expectedMessage)
    {
        htmlTestHelper.verifyAlert(expectedMessage);
    }

    @Override
    public void verifyConfirm(String expectedMessage)
    {
        htmlTestHelper.verifyConfirm(expectedMessage);
    }

    @Override
    public String idWithParam(String idElement, long idEntity)
    {
        return htmlTestHelper.idWithParam(idElement, idEntity);
    }
}