package com.LuiizTeixeira.locadora.model;

import com.LuiizTeixeira.locadora.model.exception.ReservaInvalidaException;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
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

        assertThrows(ReservaInvalidaException.class, () -> new Reserva(cliente, carro, 0));
        assertDoesNotThrow(() -> new Reserva(cliente, carro, 1));

        var erro = Assertions.catchThrowable(() -> new Reserva(cliente, carro, 0));
        Assertions.assertThat(erro).isInstanceOf(ReservaInvalidaException.class);
    }

    @Test
    void deveCalcularOTotalAluguel() {

        var reserva = new Reserva(cliente, carro, 3);
        var total = reserva.calcularTotal();
        Assertions.assertThat(total).isEqualTo(150.0);

    }
}