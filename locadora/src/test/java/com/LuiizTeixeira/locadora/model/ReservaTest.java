package com.LuiizTeixeira.locadora.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    Cliente cliente;
    Carro carro;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("José");
        carro = new Carro("Hatch", 50.0);
    }

    @Test
    void deveCriarUmaReserva() {

        var dias = 5;

        var reserva = new Reserva(cliente, carro, dias);

        Assertions.assertThat(reserva).isNotNull();

    }

    @Test
    void deveDarErroAoCriarUmaReservaComDiasNegativos() {

    }

    @Test
    void deveCalcularOTotalAluguel() {

    }
}