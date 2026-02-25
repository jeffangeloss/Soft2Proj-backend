package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Flow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExecutionContextTest {

    @Mock
    private Flow flow;

    @InjectMocks
    private ExecutionContext context;

    @Test
    void killUpdateLogicMutants() {
        context.put("keyTest", "valorInicial");
        context.put("keyTest", "valorActualizado");
        assertEquals(1, context.getVariableList().size(), "ERROR: Se duplicó la variable en lugar de actualizar");
        assertEquals("valorActualizado", context.get("keyTest"), "El valor de la variable no se actualizó");
    }

    @Test
    void testGetReturnsNullWhenKeyMissing() {
        assertNull(context.get("llave_fantasma"), "Debería retornar null para llaves que no existen");
    }
}
