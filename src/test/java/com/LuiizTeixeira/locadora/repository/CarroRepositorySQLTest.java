package com.LuiizTeixeira.locadora.repository;

import com.LuiizTeixeira.locadora.entity.CarroEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@ActiveProfiles
public class CarroRepositorySQLTest {

    @Autowired
    CarroRepository repository;

    @Test
    @Sql("/sql/popular-carros.sql")
    void deveBuscarCarroPorModelo() {

        List<CarroEntity> list = repository.findByModelo("suv");

        var carro = list.stream().findFirst().get();

        assertEquals(1, list.size());

        assertThat(carro.getValorDiaria()).isEqualTo(150.0);
        assertThat(carro.getModelo()).isEqualTo("suv");
        assertThat(carro.getAno()).isEqualTo(2025);



    }
}
