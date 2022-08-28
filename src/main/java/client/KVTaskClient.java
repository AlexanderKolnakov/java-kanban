package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String apiKey;
    private int port = 8078;
    private final HttpClient client = HttpClient.newHttpClient();

    public KVTaskClient(String URL) throws IOException, InterruptedException {
        URI uri = URI.create(URL);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);    // ОШИБКА
        apiKey = response.body();
        System.out.println("Тело ответа " + apiKey);
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        String URL = "http://localhost:" + this.port + "/save/" + key + "?API_KEY=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(URL))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        System.out.println("Status " + response.statusCode());
    }

    public String load(String key) throws IOException, InterruptedException {
        String URL = "http://localhost:" + this.port + "/load/" + key + "?API_KEY=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }
}
