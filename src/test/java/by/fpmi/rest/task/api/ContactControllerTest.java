package by.fpmi.rest.task.api;

import by.fpmi.rest.task.entities.Contact;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class ContactControllerTest {


    private static final int OK_CODE = 200;
    private static final String URL = "http://localhost:8080";

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllContactsShouldReturnContacts() throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL);

        CloseableHttpResponse response = client.execute(request);
        // HttpEntity entity = response.getEntity();
        //var result = mapper.readValue(entity.getContent(),Contacts[].class);

        int executionCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(OK_CODE, executionCode);
    }

    @Test
    public void testPostShouldReturnValidCode() throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(URL);
        Contact newContact = Contact.Builder.newInstance()
                .withName("Test")
                .withSurname("Testovich")
                .withPhone("12-12-12-12")
                .build();
        String contactJson = mapper.writeValueAsString(newContact);
        request.setHeader("Content-Type", "application/json");

        request.setEntity(new StringEntity(contactJson));

        CloseableHttpResponse response = client.execute(request);
        int executionCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(OK_CODE, executionCode);
    }
}
