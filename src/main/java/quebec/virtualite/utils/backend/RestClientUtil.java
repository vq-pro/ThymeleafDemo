package quebec.virtualite.utils.backend;

import quebec.virtualite.utils.Command;

import java.util.LinkedHashMap;

public interface RestClientUtil
{
    void delete(String url, LinkedHashMap<String, Object> params);

    void delete(String url, String param);

    <T> T doWithErrorHandling(Command<T> cmd);

    <T> T get(String url, LinkedHashMap<String, Object> params, Class<T> responseType);

    <T> T get(String url, Class<T> responseType);

    <T> T get(String url, String param, Class<T> responseType);

    <T> T get(String url, String param1, String param2, Class<T> responseType);

    <T> T post(String url, Object entity, Class<T> responseType);

    <T> T post(String url, String param, Object entity, Class<T> responseType);

    void put(String url, Object entity);
}
