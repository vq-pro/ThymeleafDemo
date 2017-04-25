package quebec.virtualite.utils.ui;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages
{
    private ResourceBundleMessageSource messageSource;

    @PostConstruct
    void _init()
    {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
    }

    public String get(String key)
    {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}
