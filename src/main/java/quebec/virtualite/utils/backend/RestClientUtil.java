package quebec.virtualite.utils.backend;

import quebec.virtualite.utils.Command;

public interface RestClientUtil
{
    void delete(String url, String... params);

    <T> T doWithErrorHandling(Command<T> cmd);

    <T> T get(String url, String... params);

    <T> T get(Class<T> responseType, String url, String... params);

    <T> T post(Object entity, String url, String... params);

    <T> T post(Object entity, Class<T> responseType, String url, String... params);

    <T> T put(Object entity, String url, String... params);

    <T> T put(Object entity, Class<T> responseType, String url, String... params);
}
