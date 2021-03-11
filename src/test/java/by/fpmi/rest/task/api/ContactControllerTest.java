package by.fpmi.rest.task.api;

import by.fpmi.rest.task.entities.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ContactControllerTest {

    private static final int OK_CODE = 200;
    private static final String URL = "http://localhost:8080";

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void googleUltimateMultithreadingUltraGetTest() throws ExecutionException, InterruptedException {
        int threadsCount = 1000;
        int operationsCount = 100;

        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        List<Future<?>> results = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            results.add(service.submit(() -> {
                for (int j = 0; j < operationsCount; j++) {
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpGet request = new HttpGet(URL);
                    CloseableHttpResponse response = null;
                    try {
                        response = client.execute(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int executionCode = Objects.requireNonNull(response)
                            .getStatusLine()
                            .getStatusCode();
                    Assert.assertEquals(OK_CODE, executionCode);
                }
            }));
        }
        for (Future<?> future : results) {
            future.get();
        }
        service.shutdown();
    }

    @Test
    public void testGetAllContactsShouldReturnContacts() throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL);

        CloseableHttpResponse response;
        response = client.execute(request);

        int executionCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(OK_CODE, executionCode);
    }

    @Ignore
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

    @Ignore
    @Test
    public void testPostAndCheckData() throws IOException {

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

        CloseableHttpClient getClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(URL);

        CloseableHttpResponse getResponse = client.execute(request);
        HttpEntity entity = response.getEntity();
        var result = mapper.readValue(entity.getContent(), Contacts[].class);
    }
}
