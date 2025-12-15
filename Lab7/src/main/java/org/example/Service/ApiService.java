package org.example.Service;

import com.google.gson.*;
import org.example.Exception.ApiException;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class ApiService {

    private final HttpClient client;
    private final Gson gson;
    private final String apiUrl;


    public ApiService(HttpClient client, Gson gson, @Value("${app.api.url}")  String apiUrl) {
        this.client = client;
        this.gson = gson;
        this.apiUrl = apiUrl;
    }


    public List<Pracownik> pobierzPracownikowZApi() throws ApiException {
        List<Pracownik> wynik = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("blad");
            }

            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();


                String[] imieNazwisko = obj.get("name").getAsString().split(" ", 2);
                String imie = imieNazwisko[0];
                String nazwisko;
                if (imieNazwisko.length > 1) {
                    nazwisko = imieNazwisko[1];
                } else {
                    nazwisko = "";
                }
                String email = obj.get("email").getAsString();
                String firma = obj.get("company").getAsJsonObject().get("name").getAsString();

                Pracownik pracownik = new Pracownik(imie, nazwisko, email, firma, Stanowiska.Programista);
                wynik.add(pracownik);
            }

        } catch (IOException | InterruptedException | JsonParseException e) {
            throw new ApiException("blad pobierania danych " + e.getMessage());
        }

        return wynik;
    }
}
