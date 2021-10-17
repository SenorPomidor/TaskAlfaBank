package ru.alfabank.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.alfabank.project.service.MainService;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainService mainService;

    @Test
    void getResult() throws Exception {
        Mockito.when(mainService.getGif("USD"))
                .thenReturn(InputStream.nullInputStream());
        this.mockMvc.perform(get("/USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/gif"));
    }
}
