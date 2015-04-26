package com.betfair.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.betfair.jsonrpc.JsonResponseHandler;

public class HttpUtil {

    private final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private final String HTTP_HEADER_ACCEPT = "Accept";
    private final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";

    public HttpUtil() {
        super();
    }

    private String sendPostRequest(String param, String operation, String URL, ResponseHandler<String> reqHandler) throws Exception{
        String jsonRequest = param;

        HttpPost post = new HttpPost(URL);
        String resp = null;
        try {
            post.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json");
            post.setHeader(HTTP_HEADER_ACCEPT, "application/json");
            post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, "UTF-8");
            post.setHeader(HTTP_HEADER_X_APPLICATION, "XaBM5CwzIU1oLrgw");
            post.setHeader(HTTP_HEADER_X_AUTHENTICATION, HttpClientSSO.obtainSessionKey());
            post.setEntity(new StringEntity(jsonRequest, "UTF-8"));

            HttpClient httpClient = new DefaultHttpClient();

            HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, new Integer(10000));
            HttpConnectionParams.setSoTimeout(httpParams, new Integer(10000));

            resp = httpClient.execute(post, reqHandler);

        } catch (UnsupportedEncodingException e1) {
            //Do something

        } catch (ClientProtocolException e) {
            //Do something

        } catch (IOException ioE){
            //Do something

        }

        return resp;

    }

    public String sendPostRequestJsonRpc(String param, String operation) throws Exception {
        String apiNgURL = "https://api.betfair.com/exchange/betting/" + "json-rpc/v1";

        return sendPostRequest(param, operation, apiNgURL, new JsonResponseHandler());

    }

}
