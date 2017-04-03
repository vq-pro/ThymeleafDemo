package quebec.virtualite.utils.ui.vo;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertThat;

public class ViewObjectTest
{
    @Test
    public void equals_whenFalse() throws Exception
    {
        assertThat(new ViewObject(1, "A"),
            not(new ViewObject(1, "B")));

        assertThat(new ViewObject(1, "A"),
            not(new ViewObject(2, "A")));

        assertThat(new ViewObject(1, "A"),
            not(new ViewObject(2, "B")));
    }

    @Test
    public void equals_whenTrue() throws Exception
    {
        assertThat(new ViewObject(1, "A"),
            is(new ViewObject(1, "A")));
    }

    @Test
    public void toString_verifyOutput() throws Exception
    {
        assertThat(new ViewObject(1, "A"),
            hasToString("(1, A)"));
    }
}