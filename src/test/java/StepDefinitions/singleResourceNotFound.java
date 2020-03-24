package StepDefinitions;

import Utils.utilPayloads.Payloads;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class singleResourceNotFound {
    WebDriver driver;
    HttpResponse response;
    String appJson;
    @Given("provided url get request and status code is {int}")
    public void provided_url_get_request_and_status_code_is(Integer NotFound) throws IOException, URISyntaxException {

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


        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());

    }

    @When("response content-type is {string}")
    public void response_content_type_is(String appJson) {
        this.appJson=appJson;
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(appJson));
    }

    @Then("match the response is empty body")
    public void match_the_response_is_empty_body() throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("My response " + response.getEntity().getContent());

        Object nothing = objectMapper.readValue(response.getEntity().getContent()
                , new TypeReference<Object>() {
                });

        System.out.println(nothing + " is nothing");

        Assert.assertTrue(nothing.equals(Payloads.payloadForGetSingleResourceNotFound()));

    }
}
