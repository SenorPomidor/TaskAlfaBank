package ru.alfabank.project.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "giphy", url = "${giphy.url}")
public interface GiphyClient {
    @GetMapping
    String getFromGiphy(
            @RequestParam("api_key") String apiKey,
            @RequestParam String tag
    );
}
