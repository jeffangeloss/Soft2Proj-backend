package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionContextTest {

    private ExecutionContext context;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
    }

    @Test
    void testPutAndGetVariable() {
        context.put("testKey", "testValue");
        assertEquals("testValue", context.get("testKey"), "Mata mutante: Fallo al recuperar valor");
    }

    @Test
    void testUpdateExistingVariable() {
        context.put("key1", "initial");
        context.put("key1", "updated");

        assertEquals(1, context.getVariableList().size(), "Mata mutante: Se duplicó la variable en lugar de actualizar");
        assertEquals("updated", context.get("key1"), "Mata mutante: No se actualizó el valor correctamente");
    }

    @Test
    void testGetNonExistentVariableReturnsNull() {
        assertNull(context.get("ghostKey"), "Mata mutante: Debería retornar null para llaves inexistentes");
    }
}
