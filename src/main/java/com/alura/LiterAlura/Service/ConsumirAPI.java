package com.alura.LiterAlura.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumirAPI {
    public String consumo(String address) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Verifica se a resposta foi bem-sucedida
        if (response == null || response.statusCode() != 200) {
            System.err.println("Erro na requisição. Código de status: " + (response != null ? response.statusCode() : "null"));
            return null;
        }

        String json = response.body();
        return json;
    }
}

