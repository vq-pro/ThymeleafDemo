package quebec.virtualite.utils.backend.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import quebec.virtualite.utils.Command;
import quebec.virtualite.utils.backend.InternalServerException;
import quebec.virtualite.utils.backend.RestClientUtil;
import quebec.virtualite.utils.backend.RestError;
import quebec.virtualite.utils.backend.ServerException;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class RestClientUtilImpl implements RestClientUtil
{
    private String serverUrl;

    @Autowired
    private Environment environment;

    @Override
    public void delete(String url, String... params)
    {
        checkInit();

        getTemplate()
            .exchange(buildPathParamUrl(url, params), DELETE, null, Void.class);
    }

    public <T> T doWithErrorHandling(Command<T> cmd)
    {
        try
        {
            return cmd.run();
        }
        catch (HttpClientErrorException e)
        {
            String serverExceptionName = getCauseExceptionName(e);

            throw (serverExceptionName != null)
                  ? new ServerException(serverExceptionName)
                  : new InternalServerException(e);
        }
        catch (Exception e)
        {
            throw new InternalServerException(e);
        }
    }

    @Override
    public <T> T get(String url, String... params)
    {
        return get((Class<T>) Void.class, url, params);
    }

    @Override
    public <T> T get(Class<T> responseType, String url, String... params)
    {
        //        HttpAuthentication httpAuthentication = new
        // HttpBasicAuthentication("username", "password");
        //        HttpHeaders requestHeaders = new HttpHeaders();
        //        requestHeaders.setAuthorization(httpAuthentication);
        //
        //        HttpEntity<?> httpEntity = new HttpEntity<Object>
        // (requestHeaders);

        //        String plainCreds = SecurityConfig.USERNAME + ":" +
        // SecurityConfig.PASSWORD;
        //        byte[] plainCredsBytes = plainCreds.getBytes();
        //        byte[] base64CredsBytes = Base64.encodeBase64
        // (plainCredsBytes);
        //        String base64Creds = new String(base64CredsBytes);
        //
        //        HttpHeaders headers = new HttpHeaders();
        //        headers.add("Authorization", "Basic " + base64Creds);
        //
        //        HttpEntity<String> httpEntity = new HttpEntity<String>
        // (headers);

        //        MultiValueMap<String, String> headers = new
        // LinkedMultiValueMap<String, String>();
        //        headers.add("HeaderName", "value");
        //        headers.add("Content-Type", "application/json");
        //
        //        HttpEntity httpEntity = new HttpEntity(headers);

        //        final ResponseEntity<T> response = getTemplate()
        //            .exchange(serverUrl + buildPathParamUrl, HttpMethod.GET, getToken(),
        // responseType);

        checkInit();

        return getTemplate()
            //            .exchange(serverUrl + buildPathParamUrl, HttpMethod.GET,
            // httpEntity, responseType);
            .exchange(buildPathParamUrl(url, params), GET, null, responseType)
            .getBody();
    }

    @Override
    public <T> T post(Object entity, String url, String... params)
    {
        return post(entity, (Class<T>) Void.class, url, params);
    }

    @Override
    public <T> T post(Object entity, Class<T> responseType, String url, String... params)
    {
        checkInit();

        return getTemplate()
            .exchange(
                buildPathParamUrl(url, params),
                POST,
                new HttpEntity<>(entity),
                responseType)
            .getBody();
    }

    @Override
    public <T> T put(Object entity, String url, String... params)
    {
        return put(entity, (Class<T>) Void.class, url, params);
    }

    @Override
    public <T> T put(Object entity, Class<T> responseType, String url, String... params)
    {
        return getTemplate()
            .exchange(
                buildPathParamUrl(url, params),
                PUT,
                new HttpEntity<>(entity),
                responseType)
            .getBody();
    }

    private String buildPathParamUrl(String url, String... params)
    {
        StringBuilder builder = new StringBuilder()
            .append(serverUrl)
            .append(url);

        for (String param : params)
        {
            builder
                .append("/")
                .append(param);
        }

        return builder.toString();
    }

    private void checkInit()
    {
        if (serverUrl == null)
        {
            this.serverUrl = environment.getProperty("hostname") +
                             ":" +
                             environment.getProperty("local.server.port");
        }
    }

    private String getCauseExceptionName(final HttpStatusCodeException e)
    {
        try
        {
            String responseBodyAsString = e.getResponseBodyAsString();

            if (StringUtils.isEmpty(responseBodyAsString))
                return null;

            final RestError error = new ObjectMapper()
                .readValue(responseBodyAsString, RestError.class);

            if (error.exception == null)
                return null;

            return error.exception.substring(error.exception.lastIndexOf('.') + 1);
        }
        catch (Exception e2)
        {
            throw new RuntimeException(e2);
        }
    }

    private RestTemplate getTemplate()
    {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
            .add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }

    //    private HttpEntity<String> getToken()
    //    {
    // TODO Re-enable REST security
    //        String plainCreds = SecurityConfig.USERNAME + ":" +
    // SecurityConfig
    //            .PASSWORD;
    //        byte[] plainCredsBytes = plainCreds.getBytes();
    //        byte[] base64CredsBytes = Base64.encodeBase64
    // (plainCredsBytes);
    //        String base64Creds = new String(base64CredsBytes);
    //
    //        HttpHeaders headers = new HttpHeaders();
    //        headers.add("Authorization", "Basic " + base64Creds);
    //
    //        return new HttpEntity<String>(headers);

    //        return null;
    //    }
}
