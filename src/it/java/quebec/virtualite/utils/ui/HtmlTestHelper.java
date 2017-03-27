package quebec.virtualite.utils.ui;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Component
public class HtmlTestHelper implements HtmlTestHelperFunctions
{
    private WebClient client;
    private HtmlPage page;
    private ResourceBundleMessageSource messageSource;
    private String actualAlertMessage;
    private String actualConfirmationMessage;
    private String actualPromptMessage;
    private boolean displayAlert;
    private boolean displayConfirmation;
    private boolean displayPrompt;
    private boolean resultConfirmation;
    private String resultPrompt;

    @Resource
    private WebApplicationContext context;

    @Override
    public HtmlPage click(String id) throws IOException
    {
        return page = element(id).click();
    }

    @Override
    public String dump()
    {
        return page.asXml();
    }

    @Override
    public DomElement element(String id)
    {
        DomElement element = page.getElementById(id);
        assertNotNull("element '" + id + "' not found", element);

        return element;
    }

    @Override
    public String elementText(String id)
    {
        return element(id).getTextContent().trim();
    }

    @Override
    public String elementValue(String id)
    {
        return element(id).getAttribute("value");
    }

    @Override
    public List<DomElement> elements(String id)
    {
        List<DomElement> elements = page.getElementsByIdAndOrName(id);
        assertThat("elements '" + id + "' not found", elements, not(empty()));

        return elements;
    }

    @Override
    public List<String> elementsText(String id)
    {
        return elements(id).stream()
            .map(DomNode::getTextContent)
            .map(String::trim)
            .collect(toList());
    }

    @Override
    public void emptyList(String idList)
    {
        assertFalse("Expecting " + idList + " build be hidden, but it's there!",
            exists(idList));
    }

    @Override
    public boolean exists(String id)
    {
        return page.getElementById(id) != null;
    }

    @Override
    public void expectAlert()
    {
        displayAlert = true;
    }

    @Override
    public void expectConfirmCancel()
    {
        expectConfirm(false);
    }

    @Override
    public void expectConfirmOK()
    {
        expectConfirm(true);
    }

    @Override
    public void expectPromptCancel()
    {
        expectPromptOK(null);
    }

    @Override
    public void expectPromptOK(String result)
    {
        displayPrompt = true;
        resultPrompt = result;
    }

    @Override
    public HtmlPage go(String url) throws IOException
    {
        return page = client.getPage("http://localhost" + url);
    }

    @Override
    public HtmlPage go(String url, long param) throws IOException
    {
        return go(url, String.valueOf(param));
    }

    @Override
    public HtmlPage go(String url, String param) throws IOException
    {
        return page = client.getPage("http://localhost" + url + param);
    }

    @Override
    public void hidden(String id)
    {
        assertFalse("Expecting " + id + " build be hidden, but it's there!",
            exists(id));
    }

    @Override
    public String message(String key)
    {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    @Override
    public void onView(String viewName)
    {
        assertThat("Wrong view", elementValue("VIEW_NAME"), is(viewName));
    }

    @Override
    public DomNodeList<HtmlElement> options(String idSelect)
    {
        return element(idSelect).getElementsByTagName("option");
    }

    @Override
    public List<String> optionsText(String idSelect)
    {
        return options(idSelect).stream()
            .map(DomNode::getTextContent)
            .collect(toList());
    }

    @Override
    public HtmlPage selectOption(String idSelect, long idOption) throws IOException
    {
        for (HtmlElement option : options(idSelect))
        {
            if (option.getAttribute("value").equals(String.valueOf(idOption)))
            {
                return page = option.click();
            }
        }

        return null;
    }

    @Override
    public void shown(String id)
    {
        assertTrue("Expecting " + id + " but it's not there!",
            exists(id));
    }

    @Override
    public String title()
    {
        return page.getTitleText();
    }

    @Override
    public void verifyAlert(String expectedMessage)
    {
        assertNotNull("Missing alert('" + expectedMessage + "')",
            actualAlertMessage);
        assertThat("Wrong alert message",
            actualAlertMessage, is(expectedMessage));

        actualAlertMessage = null;
    }

    @Override
    public void verifyConfirm(String expectedMessage)
    {
        assertNotNull("Missing confirm('" + expectedMessage + "')",
            actualConfirmationMessage);
        assertThat("Wrong confirm message",
            actualConfirmationMessage, is(expectedMessage));

        actualConfirmationMessage = null;
    }

    @Override
    public void verifyPrompt(String expectedMessage)
    {
        assertNotNull("Missing prompt('" + expectedMessage + "')",
            actualPromptMessage);
        assertThat("Wrong prompt message",
            actualPromptMessage, is(expectedMessage));

        actualPromptMessage = null;
    }

    @Override
    public String idWithParam(String idElement, long idEntity)
    {
        return idElement + String.valueOf(idEntity);
    }

    @PreDestroy
    void _close()
    {
        client.close();
    }

    @PostConstruct
    void _init()
    {
        client = MockMvcWebClientBuilder
            .webAppContextSetup(context)
            .build();

        client.setAlertHandler((page, message) ->
        {
            assertTrue("Not expecting 'alert(" + message + ")'", displayAlert);
            displayAlert = false;

            actualAlertMessage = message;
        });

        client.setConfirmHandler((page, message) ->
        {
            assertTrue("Not expecting 'confirm(" + message + ")'", displayConfirmation);
            displayConfirmation = false;

            actualConfirmationMessage = message;
            return resultConfirmation;
        });

        client.setPromptHandler((page, message) ->
        {
            assertTrue("Not expecting 'prompt(" + message + ")'", displayPrompt);
            displayPrompt = false;

            actualPromptMessage = message;
            return resultPrompt;
        });

        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
    }

    private void expectConfirm(boolean result)
    {
        displayConfirmation = true;
        resultConfirmation = result;
    }
}
