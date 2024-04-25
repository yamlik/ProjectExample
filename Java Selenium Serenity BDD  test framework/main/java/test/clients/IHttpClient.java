package com..test.clients;

public interface IHttpClient<T,V> {

    V get(T request);

    V post(T request);

    V put(T request);

    V delete(T request);

}
