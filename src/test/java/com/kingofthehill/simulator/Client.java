package com.kingofthehill.simulator;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by patrikv on 07/03/16.
 */
public class Client {

    private final CloseableHttpClient httpclient;
    private final static String LOCALHOST = "http://localhost:8080/addLap";

    public Client() {
        httpclient = HttpClients.createDefault();
    }

    public void send(String json) {//httpPost.setHeader("Content-type", "application/json");

        if (json.isEmpty()) return;

        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost httpPost = new HttpPost(LOCALHOST);
        httpPost.setEntity(requestEntity);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String response = httpclient.execute(httpPost, responseHandler);
            //System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
