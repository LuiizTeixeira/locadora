package com.LuiizTeixeira.locadora.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;


public class ClienteTest {

    @Test
    void deveCriarClienteComNome() {
        var cliente = new Cliente("Maria");
        String nome = cliente.getNome();
        assertNotNull(nome);
        assertTrue(nome.startsWith("M"));
    }
}
