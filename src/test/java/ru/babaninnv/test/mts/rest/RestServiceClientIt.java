package ru.babaninnv.test.mts.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nikita Babanin
 * @version 1.0
 */

public class RestServiceClientIt {
    @Test
    public void add() throws IOException, InterruptedException {
        String guid = EntityUtils.toString(HttpClientBuilder.create().build().execute(new HttpGet("http://localhost:8089/task")).getEntity());
        assertThat(guid).isNotEmpty();

        String json = EntityUtils.toString(HttpClientBuilder.create().build().execute(new HttpGet("http://localhost:8089/task/" + guid)).getEntity());

        JsonNode jsonNode = new ObjectMapper().readTree(json);
        assertThat(jsonNode.get("status").asText()).isEqualTo("running");

        TimeUnit.SECONDS.sleep(15);

        json = EntityUtils.toString(HttpClientBuilder.create().build().execute(new HttpGet("http://localhost:8089/task/" + guid)).getEntity());

        jsonNode = new ObjectMapper().readTree(json);
        assertThat(jsonNode.get("status").asText()).isEqualTo("finished");
    }
}