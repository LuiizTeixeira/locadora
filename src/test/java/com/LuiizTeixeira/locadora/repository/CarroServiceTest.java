package com.LuiizTeixeira.locadora.repository;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import com.LuiizTeixeira.locadora.model.exception.EntityNotFoundException;
import com.LuiizTeixeira.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService service;

    @Mock
    CarroRepository repository;

    @Test
    void deveSalvarUmCarro() {
        CarroEntity carro = new CarroEntity("Sedan", 150.0, 2020);
        carro.setId(1L);

        when(repository.save(any())).thenReturn(carro);

        var carroSalvo = service.salvar(carro);

        assertNotNull(carroSalvo);
        assertEquals("Sedan", carroSalvo.getModelo());

        verify(repository).save(any());

    }

    @Test
    void deveDarErroAoSalvarCarroComDiariaNegativa() {
        CarroEntity carro = new CarroEntity("Sedan", 0, 2021);

        var erro = catchThrowable(() -> service.salvar(carro));

        assertThat(erro).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O valor da diária deve ser maior que zero.");

        verify(repository, never()).save(any());
    }

    @Test
    void deveArualizarUmCarro() {
        var carroExistente = new CarroEntity("Gol", 150.0, 2020);
        when(repository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAtualizado = new CarroEntity("Gol", 200.0, 2021);
        carroAtualizado.setId(1L);
        when(repository.save(any())).thenReturn(carroAtualizado);

        Long id = 1l;
        var carro = new CarroEntity("Gol", 200.0, 2021);
        var resultado = service.atualizar(id, carro);

        assertEquals(resultado.getModelo(), "Gol");
        verify(repository, times(1)).save(any());

    }

    @Test
    void deveDarErroAoAtualizarCarroInexistente() {
        Long id = 1L;
        var carro = new CarroEntity("Gol", 200.0, 2021);

        when(repository.findById(any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.atualizar(id, carro));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDarErroAoDeletarCarroInexistente() {
        Long id = 1L;

        when(repository.findById(any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.deletar(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        verify(repository, never()).delete(any());
    }

    @Test
    void deveDeletarUmCarro() {
        Long id = 1l;
        var carro = new CarroEntity("Gol", 200.0, 2021);
        when(repository.findById(any())).thenReturn(Optional.of(carro));

        service.deletar(id);

        verify(repository, times(1))
                .delete(any());

    }

    @Test
    void deveBuscarCarroPorId() {
        Long id = 1L;
        var carro = new CarroEntity("Gol", 200.0, 2021);
        when(repository.findById(any())).thenReturn(Optional.of(carro));

        var carroEncontrado = service.buscarPorId(id);

        assertThat(carroEncontrado.getModelo()).isEqualTo("Gol");
        assertThat(carroEncontrado.getValorDiaria()).isEqualTo(200.0);
        assertThat(carroEncontrado.getAno()).isEqualTo(2021);
    }

    @Test
    void deveListarTodos() {
        var carro = new CarroEntity("Gol", 200.0, 2021);
        var carro2 = new CarroEntity("Gol2", 200.0, 2021);

        var lista = List.of(carro, carro2);
        when(repository.findAll()).thenReturn(lista);

        List<CarroEntity> resultado = service.listarTodos();

        assertThat(resultado).hasSize(2);

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);

    }


}