package quebec.virtualite.utils.ui.view;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import quebec.virtualite.utils.ui.Messages;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BasePageObject
{
    @Autowired
    private WebClientService web;

    @Autowired
    private Messages messages;

    public String dump()
    {
        return web.page.asXml();
    }

    public String title()
    {
        return web.page.getTitleText();
    }

    protected HtmlPage click(String id) throws IOException
    {
        return web.page = element(id).click();
    }

    protected HtmlPage clickWithPopups(String id, Popup... popups) throws IOException
    {
        stream(popups)
            .forEach(Popup::expect);

        web.page = click(id);

        stream(popups)
            .forEach(Popup::verify);

        return web.page;
    }

    protected HtmlElement element(String id)
    {
        HtmlElement element = (HtmlElement) web.page.getElementById(id);
        assertNotNull("element '" + id + "' not found", element);

        return element;
    }

    protected String elementText(String id)
    {
        return element(id).getTextContent().trim();
    }

    protected String elementValue(String id)
    {
        return element(id).getAttribute("value");
    }

    protected List<DomElement> elements(String id)
    {
        List<DomElement> elements = web.page.getElementsByIdAndOrName(id);
        assertThat("elements '" + id + "' not found", elements, not(empty()));

        return elements;
    }

    protected List<String> elementsText(String id)
    {
        return elements(id)
            .stream()
            .map(DomNode::getTextContent)
            .map(String::trim)
            .collect(toList());
    }

    protected List<String> elementsValue(String id)
    {
        return elements(id)
            .stream()
            .map(element -> element.getAttribute("value"))
            .map(String::trim)
            .collect(toList());
    }

    protected boolean exists(String id)
    {
        return web.page.getElementById(id) != null;
    }

    protected HtmlPage go(String url) throws IOException
    {
        return web.page = web.client.getPage("http://localhost" + url);
    }

    protected HtmlPage go(String url, long param) throws IOException
    {
        return go(url, String.valueOf(param));
    }

    protected boolean hasButton(String id, String keyMessage)
    {
        return exists(id) && elementText(id).equals(message(keyMessage));
    }

    protected String idWithParam(String idElement, long... idEntities)
    {
        StringBuilder builder = new StringBuilder()
            .append(idElement)
            .append(idEntities[0]);

        for (int i = 1; i < idEntities.length; i++)
        {
            builder
                .append("-")
                .append(idEntities[i]);
        }

        return builder.toString();
    }

    protected String idWithParam(String idElement, String... idEntities)
    {
        StringBuilder builder = new StringBuilder()
            .append(idElement)
            .append(idEntities[0]);

        for (int i = 1; i < idEntities.length; i++)
        {
            builder
                .append("-")
                .append(idEntities[i]);
        }

        return builder.toString();
    }

    protected String message(String key)
    {
        return messages.get(key);
    }

    protected String optionSelected(String idSelect)
    {
        return options(idSelect)
            .stream()
            .filter(HtmlOption::isSelected)
            .findFirst()
            .map(htmlOption -> htmlOption.getAttribute("value"))
            .orElse(null);
    }

    protected List<HtmlOption> options(String idSelect)
    {
        return element(idSelect)
            .getElementsByTagName("option")
            .stream()
            .map(htmlElement -> (HtmlOption) htmlElement)
            .collect(toList());
    }

    protected List<String> optionsText(String idSelect)
    {
        return options(idSelect)
            .stream()
            .map(DomNode::getTextContent)
            .collect(toList());
    }

    protected HtmlPage selectOption(String idSelect, long idOption) throws IOException
    {
        return selectOption(idSelect, String.valueOf(idOption));
    }

    protected HtmlPage selectOption(String idSelect, String idOption) throws IOException
    {
        for (HtmlOption option : options(idSelect))
        {
            if (option.getAttribute("value").equals(String.valueOf(idOption)))
            {
                return web.page = option.click();
            }
        }

        fail(format("Option '%s' not found in select '%s'", idOption, idSelect));
        return null;
    }

    private HtmlPage go(String url, String param) throws IOException
    {
        return web.page = web.client.getPage("http://localhost" + url + param);
    }
}
