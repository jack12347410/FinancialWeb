package com.jack.financialweb.util;

import okhttp3.*;

import java.io.IOException;

public class HttpUtil {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    /**
     * OKHTTP4 Post
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String Post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
