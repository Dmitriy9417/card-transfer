package ru.netology.cardtransfer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Выполняется только вручную — требует собранного jar и docker-compose")
@Testcontainers
public class IntegrationTest {

    @Container
    public static DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(new File("docker-compose.yml"))
                    .withExposedService("backend", 5500)
                    .withExposedService("frontend", 3000, Wait.forHttp("/").forStatusCode(200));

    static TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void backendTransferTest() {
        Integer port = compose.getServicePort("backend", 5500);
        String url = "http://localhost:" + port + "/transfer";

        String json = """
        {
          "cardFromNumber": "1234567812345678",
          "cardToNumber": "8765432187654321",
          "cardFromValidTill": "12/25",
          "cardFromCVV": "123",
          "amount": {
            "value": 1000,
            "currency": "RUR"
          }
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("operationId"));
    }

    @Test
    void frontendIndexCheck() throws Exception {
        Integer port = compose.getServicePort("frontend", 3000);
        String urlString = "http://127.0.0.1:" + port + "/";
        System.out.println("frontend port for test: " + port + ", url: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(60_000); // 60 seconds timeout
        connection.setReadTimeout(60_000);

        int status = connection.getResponseCode();
        System.out.println("HTTP status: " + status);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append('\n');
        }
        in.close();
        connection.disconnect();

        System.out.println("Response body:\n" + content);

        assertEquals(200, status);
        assertTrue(content.toString().toLowerCase().contains("<html"));
    }

}
