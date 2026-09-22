package com.LuiizTeixeira.locadora.repository;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    @Autowired
    CarroRepository repository;

    CarroEntity carro;

    @BeforeEach
    void setUp() {
        carro = new CarroEntity("Honda civic", 100.0, 2025);

    }

    @Test
    void deveSalvarCarro() {

     var entity = new CarroEntity("Fusca", 100.0, 2025);

     repository.save(entity);

     assertNotNull(entity.getId());
    }

    @Test
    void deveBuscarCarroPorId() {

        var carroSalvo = repository.save(carro);

        Optional<CarroEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isPresent();
        assertThat(carroEncontrado.get().getModelo()).isEqualTo("Honda civic");

    }

    @Test
    void deveAtualizarCarro(){

        var carroSalvo = repository.save(carro);

        carroSalvo.setModelo("Honda Fit");
        carroSalvo.setValorDiaria(120.0);
        carroSalvo.setAno(2026);

        var carroAtualizado = repository.save(carroSalvo);

        assertThat(carroAtualizado.getModelo()).isEqualTo("Honda Fit");
        assertThat(carroAtualizado.getValorDiaria()).isEqualTo(120.0);
        assertThat(carroAtualizado.getAno()).isEqualTo(2026);
    }

    @Test
    void deveDeletarCarro() {

        var carroSalvo = repository.save(carro);

        repository.deleteById(carroSalvo.getId());

        Optional<CarroEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isEmpty();
    }



}