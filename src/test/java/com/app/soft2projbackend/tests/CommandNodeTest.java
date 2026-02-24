package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.nodetypes.CommandNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CommandNodeTest {

    @Mock
    private ExecutionContext context;

    @InjectMocks
    private CommandNode commandNode;

    @Test
    void killCommandKeyMutants() throws Exception {
        commandNode.setId("C1");
        commandNode.setCommand("echo test");

        // Ejecutamos (solo si es Windows para que no falle el ProcessBuilder)
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            commandNode.execute(context);

            ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
            verify(context, atLeastOnce()).put(keyCaptor.capture(), any());

            // Verificamos que las llaves contengan el ID exacto (mata mutantes de concatenación)
            assertTrue(keyCaptor.getAllValues().contains("outputC1"), "ERROR: Mutante alteró la llave 'output'");
            assertTrue(keyCaptor.getAllValues().contains("conditionResultC1"), "ERROR: Mutante alteró la llave de condición");
        }
    }
}
