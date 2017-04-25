package quebec.virtualite.utils.ui.view;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import static quebec.virtualite.utils.ui.view.PopupAlert.alertHandler;
import static quebec.virtualite.utils.ui.view.PopupConfirm.confirmHandler;
import static quebec.virtualite.utils.ui.view.PopupPrompt.promptHandler;

@Service
class WebClientService
{
    WebClient client;
    HtmlPage page;

    @Resource
    private WebApplicationContext context;

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

        client.setAlertHandler((page, message) -> alertHandler(message));
        client.setConfirmHandler((page, message) -> confirmHandler(message));
        client.setPromptHandler((page, message) -> promptHandler(message));
    }
}
