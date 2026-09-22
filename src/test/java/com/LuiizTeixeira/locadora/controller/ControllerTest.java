package com.LuiizTeixeira.locadora.controller;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import com.LuiizTeixeira.locadora.model.exception.EntityNotFoundException;
import com.LuiizTeixeira.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        when(carroService.salvar(any())).thenReturn(carro);

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

    @Test
    void deveObterDetalhesCarro() throws Exception {
        when(carroService.buscarPorId(any())).thenReturn(new CarroEntity(
                1L, "opala", 250.00, 2028
        ));

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros/1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.modelo").value("opala"))
                .andExpect(jsonPath("$.valorDiaria").value(250.00))
                .andExpect(jsonPath("$.ano").value(2028));


    }

    @Test
    void deveRetornarNotFoundAoObterDetalhesCarroInexistente() throws Exception {
        when(carroService.buscarPorId(any())).thenThrow(EntityNotFoundException.class);

        mvc.perform(
                MockMvcRequestBuilders.get("/carros/999")
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveListarCarro() throws Exception {
        var listagem = List.of(
                new CarroEntity(1L, "Argo", 100, 2027),
                new CarroEntity(2L, "Celta", 80, 2028)
        );

        when(carroService.listarTodos()).thenReturn(listagem);

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Argo"))
                .andExpect(jsonPath("$[1].modelo").value("Celta"));

    }

    @Test
    void deveAtualizarUmCarro() throws Exception {
        when(carroService.atualizar(any(), any()))
                .thenReturn(new CarroEntity(1L, "celta", 100, 2025));

        String json = """
                {
                    "modelo": "celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
                """;

        mvc.perform(
                MockMvcRequestBuilders.put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarCarroInexistente() throws Exception {
        when(carroService.atualizar(any(), any()))
                .thenThrow(EntityNotFoundException.class);

        String json = """
                {
                    "modelo": "celta",
                    "valorDiaria": 100,
                    "ano": 2025
                }
                """;

        mvc.perform(
                MockMvcRequestBuilders.put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarUmCarro() throws Exception {
        doNothing().when(carroService).deletar(any());
        mvc.perform(
                MockMvcRequestBuilders.delete("/carros/1")
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarUmCarroInexistente() throws Exception {
        doThrow(EntityNotFoundException.class).when(carroService).deletar(any());
        mvc.perform(
                MockMvcRequestBuilders.delete("/carros/1")
        ).andExpect(status().isNotFound());
    }

}
