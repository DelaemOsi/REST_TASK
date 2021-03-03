package by.fpmi.rest.task.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ContactControllerTest {

    private static final int OK_CODE = 200;

    @Test
    public void testGetAllContactsShouldReturnContacts() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080");

        CloseableHttpResponse response = client.execute(request);
        int executionCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(OK_CODE, executionCode);

    }


}
