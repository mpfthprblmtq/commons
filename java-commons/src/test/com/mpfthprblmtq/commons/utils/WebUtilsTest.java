package com.mpfthprblmtq.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpfthprblmtq.commons.objects.RequestProperties;
import com.mpfthprblmtq.commons.objects.RequestURL;
import org.junit.jupiter.api.Test;
import testingUtils.model.PostmanEchoGetResponse;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WebUtilsTest {

    // using Postman's Echo API, which does basic API operations
    private static final String POSTMAN_ECHO_BASE_URL = "https://postman-echo.com/";

    @Test
    public void testOpenPage() {
        // TODO is this even possible to test?
    }

    @Test
    public void testGet_withRequestParameters_andCustomHeader_doesGetWithValidResponse() throws IOException {
        URL requestURL = new RequestURL().withBaseUrl(POSTMAN_ECHO_BASE_URL + "get")
                .withQueryParam("requestParam1", "requestParamValue1")
                .withQueryParam("requestParam2", "requestParamValue2")
                .buildUrl();
        RequestProperties requestProperties = new RequestProperties()
                .withProperty("custom-header", "custom-value")
                .build();

        PostmanEchoGetResponse response = WebUtils.get(requestURL, requestProperties, PostmanEchoGetResponse.class);

        assertNotNull(response);
        assertEquals(requestURL.toString(), response.getUrl());
        assertFalse(response.getArgs().isEmpty());
        assertFalse(response.getHeaders().isEmpty());
        assertEquals("requestParamValue1", response.getArgs().get("requestParam1"));
        assertEquals("requestParamValue2", response.getArgs().get("requestParam2"));
        assertEquals("custom-value", response.getHeaders().get("custom-header"));
    }

    @Test
    public void testGet_withoutRequestParameters_doesGetWithValidResponse() throws IOException {
        URL requestURL = new RequestURL().withBaseUrl(POSTMAN_ECHO_BASE_URL + "get").buildUrl();
        RequestProperties requestProperties = new RequestProperties().build();

        PostmanEchoGetResponse response = WebUtils.get(requestURL, requestProperties, PostmanEchoGetResponse.class);

        assertNotNull(response);
        assertEquals(requestURL.toString(), response.getUrl());
        assertTrue(response.getArgs().isEmpty());
        assertFalse(response.getHeaders().isEmpty());
    }

    @Test
    public void testPost_withRequestParameters_andRequestBody_doesPostWithValidResponse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL requestURL = new RequestURL().withBaseUrl(POSTMAN_ECHO_BASE_URL + "post")
                .withQueryParam("requestParam1", "requestParamValue1")
                .withQueryParam("requestParam2", "requestParamValue2")
                .buildUrl();

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("bodyKey", "bodyValue");
        String bodyString = mapper.writeValueAsString(bodyMap);

        RequestProperties requestProperties = new RequestProperties()
                .withProperty("custom-header", "custom-value")
                .withProperty("Content-Type", "application/json")
                .withBody(bodyString)
                .build();

        PostmanEchoGetResponse response = WebUtils.post(
                requestURL, true, requestProperties, PostmanEchoGetResponse.class);

        assertNotNull(response);
        assertEquals(requestURL.toString(), response.getUrl());
        assertEquals("requestParamValue1", response.getArgs().get("requestParam1"));
        assertEquals("requestParamValue2", response.getArgs().get("requestParam2"));
        assertEquals("custom-value", response.getHeaders().get("custom-header"));
        assertNotNull(response.getData());
        assertEquals("bodyValue", response.getData().get("bodyKey"));
    }

    @Test
    public void testPost_withoutCustomRequestParametersOrBody_doesPostWithValidResponse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL requestURL = new RequestURL().withBaseUrl(POSTMAN_ECHO_BASE_URL + "post").buildUrl();
        RequestProperties requestProperties = new RequestProperties()
                .withProperty("Content-Type", "application/json")
                .build();

        PostmanEchoGetResponse response = WebUtils.post(
                requestURL, true, requestProperties, PostmanEchoGetResponse.class);

        assertNotNull(response);
        assertEquals(requestURL.toString(), response.getUrl());
        assertTrue(response.getArgs().isEmpty());
        assertFalse(response.getHeaders().isEmpty());
        assertTrue(response.getData().isEmpty());
    }
}
