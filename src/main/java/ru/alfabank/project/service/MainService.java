package ru.alfabank.project.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfabank.project.client.FileClient;
import ru.alfabank.project.client.GiphyClient;
import ru.alfabank.project.client.RatesClient;
import ru.alfabank.project.properties.KeyProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDate;

@Service
public class MainService {
    private final static Gson GSON = new Gson();

    private final FileClient fileClient;

    private final GiphyClient giphyClient;

    private final RatesClient ratesClient;

    private final KeyProperties keyProperties;

    @Value("${symbols}")
    private String symbols;

    @Autowired
    public MainService(
            FileClient fileClient,
            GiphyClient giphyClient,
            RatesClient ratesClient,
            KeyProperties keyProperties
    ) {
        this.fileClient = fileClient;
        this.giphyClient = giphyClient;
        this.ratesClient = ratesClient;
        this.keyProperties = keyProperties;
    }

    public InputStream getGif(String code) {
        URI uri = getRateResult(code);

        try {
            Response response = fileClient.downloadFile(uri);
            return response.body().asInputStream();
        } catch (IOException e) {
            return InputStream.nullInputStream();
        }
    }

    public URI getRateResult(String code) {
        LocalDate date = LocalDate.now().minusDays(1);

        String currentRate = ratesClient.getCurrentRate(keyProperties.getRateKey(), symbols, code);
        String previousRate = ratesClient.getPreviousRate(keyProperties.getRateKey(), date, symbols, code);

        return compareAndGetGif(parseRate(currentRate), parseRate(previousRate));
    }

    public URI getLinkFromGiphy(String result) {
        JsonObject jsonObject = GSON.fromJson(result, JsonObject.class);
        jsonObject = jsonObject.getAsJsonObject("data");
        jsonObject = jsonObject.getAsJsonObject("images");
        jsonObject = jsonObject.getAsJsonObject("downsized");

        return GSON.fromJson(jsonObject.get("url"), URI.class);
    }

    private double parseRate(String rate) {
        JsonObject jsonObject = GSON.fromJson(rate, JsonObject.class);
        jsonObject = jsonObject.getAsJsonObject("rates");

        return jsonObject.get(symbols).getAsDouble();
    }

    private URI compareAndGetGif(Double currentRate, Double previousRate) {
        if (currentRate > previousRate) {
            String rich = giphyClient.getFromGiphy(keyProperties.getGiphyKey(), "rich");

            return getLinkFromGiphy(rich);
        } else {
            String broke = giphyClient.getFromGiphy(keyProperties.getGiphyKey(),"broke");

            return getLinkFromGiphy(broke);
        }
    }
}
