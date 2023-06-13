package com.starling.client;

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
        when(httpResponse.body()).thenReturn("test response");
        
        String result = httpClientWrapper.get(url);

        verify(logger, times(1)).info("Sending GET request to {}", url);
        Assertions.assertEquals("test response", result);
    }

    @Test
    public void testGetException() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Failed"));
            httpClientWrapper.get(url);
        });
    }

    @Test
    public void testPut() throws Exception {
        String payload = "test payload";

        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("test response");

        String result = httpClientWrapper.put(url, payload);

        verify(logger, times(1)).info("Sending PUT request to {}", url);
        Assertions.assertEquals("test response", result);
    }

    @Test
    public void testPutException() {
        String payload = "test payload";

        Assertions.assertThrows(RuntimeException.class, () -> {
            when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Failed"));
            httpClientWrapper.put(url, payload);
        });
    }
}
