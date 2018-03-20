package com.cellpointmobile.repoviewer.data.service;

import com.apollographql.apollo.ApolloClient;
import com.cellpointmobile.repoviewer.BuildConfig;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Graphql service creates ApolloClient
 */
public class GraphqlService {

    public static Headers getJsonHeader() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Authorization", "Bearer a4c0d9d126616cb2a2e9371dba69b54c85cc7b44 ");
        return builder.build();
    }

    public static ApolloClient getService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request request = chain.request().newBuilder()
                                    .headers(getJsonHeader())
                                    .build();
                            return chain.proceed(request);
                        })
                .build();
        return ApolloClient.builder()
                .serverUrl(BuildConfig.GITHUB_API_URL)
                .okHttpClient(okHttpClient)
                .build();
    }
}