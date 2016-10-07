package com.kingofthehill.simulator;

import com.kingofthehill.repository.model.Lap;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by patrikv on 07/03/16.
 */
public class RestClient {

    private final CloseableHttpClient httpclient;
    private final static String LOCALHOST = "http://localhost:8080/king/laps";

    public RestClient() {
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

    public void send(Lap lap) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(LOCALHOST);
        Response response = target.request().post(Entity.entity(lap, "application/json"));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        System.out.println("Server response : \n");
        System.out.println(response.readEntity(String.class));

        response.close();
    }
}
