package quebec.virtualite.daily;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions
    (
        features = "src/features",
        format =
            {
                "html:target/cucumber-reports",
                "junit:target/cucumber-reports/cucumber.xml"
            },
        monochrome = true,
        strict = true,
        snippets = CAMELCASE,
        tags = "~@Ignore"
    )
public class CucumberIT
{
}
