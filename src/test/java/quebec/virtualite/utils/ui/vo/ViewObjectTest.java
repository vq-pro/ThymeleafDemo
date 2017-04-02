package quebec.virtualite.utils.ui.vo;

import org.junit.Test;

import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertThat;

public class ViewObjectTest
{
    @Test
    public void toString_verifyOutput() throws Exception
    {
        assertThat(new ViewObject(1, "A"),
            hasToString("(1, A)"));
    }
}