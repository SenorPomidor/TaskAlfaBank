package ru.alfabank.project.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "rates", url = "${rates.url}")
public interface RatesClient {
    @GetMapping(path = "/latest.json")
    String getCurrentRate(
            @RequestParam("app_id") String appId,
            @RequestParam String symbols,
            @RequestParam String base
    );

    @GetMapping(path = "/historical/{date}.json")
    String getPreviousRate(
            @RequestParam("app_id") String appId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam String symbols,
            @RequestParam String base
    );
}
