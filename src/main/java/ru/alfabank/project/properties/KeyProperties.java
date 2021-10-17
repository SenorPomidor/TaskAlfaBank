package ru.alfabank.project.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyProperties {
    @Value("${keys.giphy}")
    private String giphy;

    @Value("${keys.rate}")
    private String rate;

    public String getGiphyKey() { return giphy; }

    public String getRateKey() { return rate; }
}
