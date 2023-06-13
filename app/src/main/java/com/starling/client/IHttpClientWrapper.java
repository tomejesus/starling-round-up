package com.starling.client;

public interface IHttpClientWrapper {
    String get(String url);

    String put(String url, String payload);
}
