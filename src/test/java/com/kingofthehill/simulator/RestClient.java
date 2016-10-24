package com.kingofthehill.simulator;

import com.kingofthehill.repository.model.Lap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by patrikv on 07/03/16.
 */
public class RestClient {

    private final CloseableHttpClient httpclient;
    private final static String LOCALHOST = "http://localhost:8080/king/laps";
    private final Client client;
    private final WebTarget target;

    public RestClient() {
        httpclient = HttpClients.createDefault();
        client = ClientBuilder.newClient();
        target = client.target(LOCALHOST);
    }

    public void send(Lap lap) {

        Response response = target.request().post(Entity.entity(lap, "application/json"));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        response.close();
    }
}
