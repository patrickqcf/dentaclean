package org.dentaclean.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    @Test
    public void testCriado() {
        assertEquals("CRIADO", Status.CRIADO.getDescricao());
    }

    @Test
    public void testCancelado() {
        assertEquals("CANCELADO", Status.CANCELADO.getDescricao());
    }

    @Test
    public void testFinalizado() {
        assertEquals("FINALIZADO", Status.FINALIZADO.getDescricao());
    }

}