package API.SingleResourseNotFaund;


import Utils.utilPayloads.Payloads;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Properties;


public class SINGLErESOURCEnOTfOUND {
    HttpResponse response;
    String appJson;

    @Test
    public void notFound() throws URISyntaxException, IOException {


        HttpClient httpClient = HttpClientBuilder.create().build();
        //https://reqres.in/api/unknown/23
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("reqres.in").setPath("api/unknown/23");

        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("configuration.properties")));
        appJson = properties.getProperty("applicationJson");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept", appJson);
        response = httpClient.execute(httpGet);


        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("My response " + response.getEntity().getContent());

        Object nothing = objectMapper.readValue(response.getEntity().getContent()
                , new TypeReference<Object>() {
                });

        System.out.println(nothing + " is nothing");

        Assert.assertTrue(nothing.equals(Payloads.payloadForGetSingleResourceNotFound()));


        System.out.println(response.getEntity().getContentType().getValue());
        System.out.println(response.getStatusLine().getStatusCode());

        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(appJson));
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());


    }
}
