package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String url;
    private String apiToken;
    private final static String URN_REGISTER = "/register";
    private final static String URN_LOAD = "/load";
    private final static String URN_SAVE = "/save";

    public KVTaskClient(int port) {
        url = "http://localhost:" + port;
        apiToken = register(url);
    }



    // ОСТАНОВИЛСЯ ТУТ В ПОИСКАХ ОШИБКИ ТЕСТА 1.26.13 ВИДЕО




    private String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + URN_REGISTER)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                System.out.println("Ошибка при регистрации. Статус код - " + response.statusCode());
            }
            return response.body();
        } catch (Exception exception) {
            System.out.println("Ошибка при регистрации   ---   register");
        }
        return "";
    }



    // НЕ ТОЧНО

    public String load(String key) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + URN_LOAD + "/" +  key+ "/"+ apiToken)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                System.out.println("Ошибка при загрузке. Статус код - " + response.statusCode());
            }
            return response.body();
        } catch (Exception exception) {
            System.out.println("Ошибка при загрузке");
        }
        return "";
    }

    public void put(String key, String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + URN_SAVE + "/" +  key+ "/"+ apiToken)).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                System.out.println("Ошибка при сохранении. Статус код - " + response.statusCode());
            }
        } catch (Exception exception) {
            System.out.println("Ошибка при сохранении");
        }
    }
}
