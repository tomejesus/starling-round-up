package com.starling.client;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

public class HttpClientWrapperTest {
    @Mock
    private HttpClient httpClient;

    @Mock
    private Logger logger;

    @Mock
    private HttpResponse<Object> httpResponse;

    private HttpClientWrapper httpClientWrapper;

    private String url = "http://example.com";
    private String bearerToken = "token";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        httpClientWrapper = new HttpClientWrapper(httpClient, bearerToken, logger);
    }

    @Test
    public void testGet() throws Exception {
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("test response");
        
        String result = httpClientWrapper.get(url);

        verify(logger, times(1)).info("Sending GET request to {}", url);
        Assertions.assertEquals("test response", result);
    }

    @Test
    public void testGetClientError() throws IOException, InterruptedException {
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(400);

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpClientWrapper.get(url);
        });
    }

    @Test
    public void testGetServerError() throws IOException, InterruptedException {
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(500);

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpClientWrapper.get(url);
        });
    }

    @Test
    public void testPut() throws Exception {
        String payload = "test payload";

        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("test response");

        String result = httpClientWrapper.put(url, payload);

        verify(logger, times(1)).info("Sending PUT request to {}", url);
        Assertions.assertEquals("test response", result);
    }

    @Test
    public void testPutClientError() throws IOException, InterruptedException {
        String payload = "test payload";

        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(400);

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpClientWrapper.put(url, payload);
        });
    }

    @Test
    public void testPutServerError() throws IOException, InterruptedException {
        String payload = "test payload";

        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(500);

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpClientWrapper.put(url, payload);
        });
    }
}
