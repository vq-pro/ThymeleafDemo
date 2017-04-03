package quebec.virtualite.utils.ui;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public interface HtmlTestHelperFunctions
{
    HtmlPage click(String id) throws IOException;

    String dump();

    DomElement element(String id);

    String elementText(String id);

    String elementValue(String id);

    List<DomElement> elements(String id);

    List<String> elementsText(String id);

    void emptyList(String idList);

    boolean exists(String id);

    void expectAlert();

    void expectConfirmCancel();

    void expectConfirmOK();

    void expectPromptCancel();

    void expectPromptOK(String result);

    HtmlPage go(String url) throws IOException;

    HtmlPage go(String url, long param) throws IOException;

    HtmlPage go(String url, String param) throws IOException;

    void hidden(String idButton);

    String idWithParam(String idElement, long idEntity);

    String message(String key);

    void onView(String viewName);

    String optionSelected(String idSelect);

    List<HtmlOption> options(String idSelect);

    List<String> optionsText(String idSelect);

    HtmlPage selectOption(String idSelect, long idOption) throws IOException;

    HtmlPage selectOption(String idSelect, String textOption) throws IOException;

    void shown(String idButton);

    String title();

    void verifyAlert(String expectedMessage);

    void verifyConfirm(String expectedMessage);

    void verifyPrompt(String expectedMessage);
}
