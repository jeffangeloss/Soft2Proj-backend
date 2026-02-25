package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.nodetypes.CommandNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandNodeTest {

    @Mock
    private ExecutionContext context;

    @InjectMocks
    private CommandNode commandNode;

    @Test
    void killCommandLogicMutants() throws Exception {
        commandNode.setId("C1");
        commandNode.setCommand("echo primera & echo segunda & echo linea_final");
        commandNode.execute(context);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> valCaptor = ArgumentCaptor.forClass(Object.class);
        verify(context, atLeastOnce()).put(keyCaptor.capture(), valCaptor.capture());

        assertTrue(keyCaptor.getAllValues().contains("outputC1"), "La llave 'outputC1' no fue generada");
        assertTrue(valCaptor.getAllValues().contains("linea_final"), "No se capturó la última línea del comando");
        assertTrue(keyCaptor.getAllValues().contains("conditionResultC1"), "Falta la llave de condición");
        assertTrue(valCaptor.getAllValues().contains(true), "El resultado de la condición debería ser true");
    }

    @Test
    void testInvalidArgumentException() {
        commandNode.setCommand(null);

        assertThrows(InvalidArgumentException.class, () -> commandNode.execute(context),
                "Mata mutante: La protección contra comandos nulos fue alterada");
    }

    @Test
    void testStopOnFailPolicy() {
        commandNode.setPolitica(PoliticaError.STOP_ON_FAIL);
        commandNode.setCommand("comando_erroneo_para_test");
        assertThrows(RuntimeException.class, () -> commandNode.execute(context));
    }
}
