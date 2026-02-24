package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.nodetypes.StartNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StartNodeTest {

    @Mock
    private ExecutionContext context;

    @InjectMocks
    private StartNode startNode;

    @Test
    void killStartNodeMutants() {
        startNode.execute(context);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);

        verify(context).put(keyCaptor.capture(), valueCaptor.capture());

        assertEquals("started", keyCaptor.getValue(), "ERROR: Mutante alteró la clave de inicio");
        assertEquals(true, valueCaptor.getValue(), "ERROR: Mutante alteró el valor de inicio");
    }
}
