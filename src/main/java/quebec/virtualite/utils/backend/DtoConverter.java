package quebec.virtualite.utils.backend;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter
{
    public static <T> T build(Class<T> targetClass, Object input) throws ConverterException
    {
        if (input == null)
            return null;

        if (targetClass.equals(input.getClass()))
            throw new ConverterException();

        try
        {
            String json = new ObjectMapper().writeValueAsString(input);
            return new ObjectMapper().readValue(json, targetClass);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> build(Class<T> targetClass, List<?> input)
    {
        if (input == null)
            return null;

        return input
            .stream()
            .map((item) -> build(targetClass, item))
            .collect(Collectors.toList());
    }
}
