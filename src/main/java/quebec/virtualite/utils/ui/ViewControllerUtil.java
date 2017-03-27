package quebec.virtualite.utils.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ViewControllerUtil
{
    public static String urlDecode(String input)
    {
        try
        {
            return URLDecoder.decode(input, "UTF-8").trim();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String urlEncode(String input)
    {
        try
        {
            return URLEncoder.encode(input, "UTF-8").trim();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
