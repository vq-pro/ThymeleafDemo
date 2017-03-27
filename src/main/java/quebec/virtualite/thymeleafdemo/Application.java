package quebec.virtualite.thymeleafdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quebec.virtualite.thymeleafdemo.backend.data.Database;
import quebec.virtualite.thymeleafdemo.backend.data.Item;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages =
    {
        "quebec.virtualite.thymeleafdemo.*",
        "quebec.virtualite.utils.*"
    })
public class Application
{
    public static final String BASE_URL = "/thymeleafDemo";

    @Autowired
    private Database db;

    public static void main(final String[] args)
        throws InterruptedException
    {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init()
    {
        db.deleteAllItems();

        db.save(new Item("B"),
            new Item("A"),
            new Item("C"));
    }
}