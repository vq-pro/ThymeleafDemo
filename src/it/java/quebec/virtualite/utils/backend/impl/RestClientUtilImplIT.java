package quebec.virtualite.utils.backend.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import quebec.virtualite.utils.backend.InternalServerException;
import quebec.virtualite.utils.backend.ServerException;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RestClientUtilImplIT
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private RestClientUtilImpl restClientUtil = new RestClientUtilImpl();
    private LinkedHashMap<String, Object> params = new LinkedHashMap<>();

    @Test
    public void buildUrlParams_withOneParam() throws Exception
    {
        // Given
        params.put("PARM", 1);

        // When
        String urlSuffix = restClientUtil.buildUrlParams(params);

        // Then
        assertThat(urlSuffix, is("?PARM={PARM}"));
    }

    @Test
    public void buildUrlParams_withTwoParams() throws Exception
    {
        // Given
        params.put("PARM1", 1);
        params.put("PARM2", 2);

        // When
        String url = restClientUtil.buildUrlParams(params);

        // Then
        assertThat(url, is("?PARM1={PARM1}&PARM2={PARM2}"));
    }

    @Test
    public void buildUrlParams_withTwoParamsInReverseOrder() throws Exception
    {
        // Given
        params.put("PARM2", 2);
        params.put("PARM1", 1);

        // When
        String url = restClientUtil.buildUrlParams(params);

        // Then
        assertThat(url, is("?PARM2={PARM2}&PARM1={PARM1}"));
    }

    @Test
    public void doWithErrorHandling_withHttpClientErrorCarryingUserException() throws Exception
    {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage(NumberFormatException.class.getSimpleName());

        // Given
        String restError =
            "{"
            + "\"timestamp\":1490366015856,"
            + "\"status\":400,"
            + "\"error\":\"Bad Request\","
            + "\"exception\":\"java.lang.NumberFormatException\","
            + "\"message\":\"No message available\","
            + "\"path\":\"/REST/url\"}";

        // When
        restClientUtil.doWithErrorHandling(() ->
        {
            throw new HttpClientErrorException(
                BAD_REQUEST,
                "400",
                restError.getBytes(),
                Charset.defaultCharset());
        });
    }

    @Test
    public void doWithErrorHandling_withHttpClientErrorWithoutUserException() throws Exception
    {
        expectedException.expect(InternalServerException.class);
        expectedException.expectCause(instanceOf(HttpClientErrorException.class));
        expectedException.expectMessage("404 404");

        // Given
        String restError =
            "{"
            + "\"timestamp\":1490370759574,"
            + "\"status\":404,"
            + "\"error\":\"Not Found\","
            + "\"message\":\"No message available\","
            + "\"path\":\"/REST/renameRecipe/19/AAA\"}";

        // When
            restClientUtil.doWithErrorHandling(() ->
            {
                throw new HttpClientErrorException(
                    NOT_FOUND,
                    "404",
                    restError.getBytes(),
                    Charset.defaultCharset());
            });
    }

    @Test
    public void doWithErrorHandling_withHttpServerError() throws Exception
    {
        expectedException.expect(InternalServerException.class);
        expectedException.expectCause(instanceOf(HttpServerErrorException.class));
        expectedException.expectMessage("404 404");

        // Given
        String restError =
            "{"
            + "\"timestamp\":1490370759574,"
            + "\"status\":404,"
            + "\"error\":\"Not Found\","
            + "\"message\":\"No message available\","
            + "\"path\":\"/REST/renameRecipe/19/AAA\"}";

        // When
        restClientUtil.doWithErrorHandling(() ->
        {
            throw new HttpServerErrorException(
                NOT_FOUND,
                "404",
                restError.getBytes(),
                Charset.defaultCharset());
        });
    }

    @Test
    public void doWithErrorHandling_withOtherServerError() throws Exception
    {
        expectedException.expect(InternalServerException.class);
        expectedException.expectCause(instanceOf(NumberFormatException.class));
        expectedException.expectMessage("Some message");

        // When
        restClientUtil.doWithErrorHandling(() ->
        {
            throw new NumberFormatException("Some message");
        });
    }
}
