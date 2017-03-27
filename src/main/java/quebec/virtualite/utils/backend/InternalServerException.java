package quebec.virtualite.utils.backend;

public class InternalServerException extends RuntimeException
{
    public InternalServerException(Exception cause)
    {
        super(cause);
    }
}
