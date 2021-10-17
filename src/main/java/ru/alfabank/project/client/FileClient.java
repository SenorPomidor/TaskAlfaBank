package ru.alfabank.project.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "file", url = "feignUrl")
public interface FileClient {
    @GetMapping
    Response downloadFile(URI url);
}
