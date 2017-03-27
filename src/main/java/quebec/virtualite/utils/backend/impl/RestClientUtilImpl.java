package quebec.virtualite.utils.backend.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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

import java.util.LinkedHashMap;

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
    public void delete(String url, LinkedHashMap<String, Object> params)
    {
        checkInit();

        getTemplate().exchange(
            serverUrl + url + buildUrlParams(params),
            DELETE,
            null,
            Void.class,
            params);
    }

    @Override
    public void delete(String url, String param)
    {
        checkInit();

        ResponseEntity<Void> response = getTemplate().exchange(
            buildPathParamUrl(url, param),
            DELETE,
            null,
            Void.class);
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
    public <T> T get(final String url, LinkedHashMap<String, Object> params, Class<T> responseType)
    {
        checkInit();

        ResponseEntity<T> response = getTemplate().exchange(
            serverUrl + url + buildUrlParams(params),
            GET,
            null,
            responseType,
            params);

        return response.getBody();
    }

    @Override
    public <T> T get(final String url, Class<T> responseType)
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

        final ResponseEntity<T> response = getTemplate()
            //            .exchange(serverUrl + buildPathParamUrl, HttpMethod.GET,
            // httpEntity, responseType);
            .exchange(serverUrl + url, GET, null, responseType);

        return response.getBody();
    }

    @Override
    public <T> T get(String url, String param, Class<T> responseType)
    {
        checkInit();

        final ResponseEntity<T> response = getTemplate().exchange(
            buildPathParamUrl(url, param),
            GET,
            null,
            responseType);

        return response.getBody();
    }

    @Override
    public <T> T get(String url, String param1, String param2, Class<T> responseType)
    {
        checkInit();

        final ResponseEntity<T> response = getTemplate()
            .exchange(
                buildPathParamUrl(url, param1, param2),
                GET,
                null,
                responseType);

        return response.getBody();
    }

    @Override
    public <T> T post(final String url, final Object entity, Class<T> responseType)
    {
        return post(url, "", entity, responseType);
    }

    @Override
    public <T> T post(final String url, String param, final Object entity, Class<T>
        responseType)
    {
        checkInit();

        ResponseEntity<T> response = getTemplate().exchange(
            buildPathParamUrl(url, param),
            POST,
            new HttpEntity<>(entity),
            responseType);

        return response.getBody();
    }

    @Override
    public void put(final String url, Object entity)
    {
        final ResponseEntity<Void> response = getTemplate()
            .exchange(
                buildUrl(url),
                PUT,
                new HttpEntity<>(entity),
                Void.class);
    }

    // Must use LinkedHashMap parm type build maintain the params
    // in the order of entry.
    String buildUrlParams(LinkedHashMap<String, Object> params)
    {
        StringBuilder builder = null;

        for (String parm : params.keySet())
        {
            if (builder == null)
            {
                builder = new StringBuilder();
                builder.append("?");
            }
            else
            {
                builder.append("&");
            }

            builder
                .append(parm)
                .append("={")
                .append(parm)
                .append("}");
        }

        return builder.toString();
    }

    private String buildPathParamUrl(String url, String param1)
    {
        return new StringBuilder()
            .append(serverUrl)
            .append(url)
            .append("/")
            .append(param1)
            .toString();
    }

    private String buildPathParamUrl(String url, String param1, String param2)
    {
        return new StringBuilder()
            .append(serverUrl)
            .append(url)
            .append("/")
            .append(param1)
            .append("/")
            .append(param2)
            .toString();
    }

    private String buildUrl(String url)
    {
        return new StringBuilder()
            .append(serverUrl)
            .append(url)
            .toString();
    }

    private void checkInit()
    {
        if (serverUrl == null)
        {
            this.serverUrl = new StringBuilder()
                .append(environment.getProperty("hostname"))
                .append(":")
                .append(environment.getProperty("local.server.port"))
                .toString();
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
