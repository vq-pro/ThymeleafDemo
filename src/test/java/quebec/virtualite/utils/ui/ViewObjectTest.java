package quebec.virtualite.utils.ui;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ViewObjectTest
{
    @Test
    public void toString_verifyOutput() throws Exception
    {
        ViewObject vo = new ViewObject(1, "A");

        assertThat(vo.toString(), is("ViewObject[id=1,text=A]"));
    }
}