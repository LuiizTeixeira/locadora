package com.LuiizTeixeira.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarroTest {

    @Test
    @DisplayName("Deve calcular o valor correto do aluguel ")
    void deveCalcularValorAluguel() {
        Carro carro = new Carro("Sedan", 100.0);
        double total = carro.calcularValorAluguel(3);
        Assertions.assertEquals(300.0, total);
    }

    @Test
    @DisplayName("Deve calcular o valor correto do aluguel com desconto ")
    void deveCalcularValorAluguelComDesconto() {
        Carro carro = new Carro("Sedan", 100.0);
        int quantidadeDias = 5;
        double total = carro.calcularValorAluguel(quantidadeDias);
        Assertions.assertEquals(450.0, total);
    }


}
