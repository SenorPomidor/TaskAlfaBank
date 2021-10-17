package ru.alfabank.project.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.project.service.MainService;

import java.io.InputStream;

@RestController
public class MainController {
    private final MainService mainService;


    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Resource> getResult(@PathVariable String code) {
        InputStream gif = mainService.getGif(code);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .body(new InputStreamResource(gif));
    }
}
