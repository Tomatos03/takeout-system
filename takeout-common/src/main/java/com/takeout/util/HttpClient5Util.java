package com.takeout.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
public class HttpClient5Util {
    /**
     * 发送 GET 请求
     *
     * @param url    请求地址
     * @param params 请求参数（可为 null）
     * @return 响应内容字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        StringBuilder urlStr = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            urlStr.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlStr.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                urlStr.append("=");
                urlStr.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                urlStr.append("&");
            }
            urlStr.deleteCharAt(urlStr.length() - 1); // 去掉最后一个&
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(urlStr.toString());
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
    }
}
