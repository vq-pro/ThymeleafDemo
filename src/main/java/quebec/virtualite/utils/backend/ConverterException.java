package quebec.virtualite.utils.backend;

public class ConverterException extends RuntimeException
{
    public ConverterException()
    {
        super("Wasteful to convert to same type");
    }
}
