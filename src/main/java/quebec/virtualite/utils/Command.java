package quebec.virtualite.utils;

@FunctionalInterface
public interface Command<T>
{
    T run() throws Exception;
}
