package com.LuiizTeixeira.locadora.controller;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import com.LuiizTeixeira.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CarroController.class)
public class ControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService carroService;

    @Test
    void deveSalvarUmCarro() throws Exception {
        CarroEntity carro = new CarroEntity(
                1L, "Fusca", 150.00, 2027);

        when(carroService.salvar(Mockito.any())).thenReturn(carro);

        String json = """
                {
                    "modelo": "Fusca",
                    "valorDiaria": 150.00,
                    "ano": 2027
                }
                """;

        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.modelo").value("Fusca"))
                .andExpect(jsonPath("$.valorDiaria").value(150.00))
                .andExpect(jsonPath("$.ano").value(2027));

    }

}
