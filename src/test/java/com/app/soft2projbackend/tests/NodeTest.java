package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.model.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    @Test
    void testNodeGettersAndSetters() {
        Node node = new Node() {
            @Override
            public void execute(ExecutionContext context) throws Exception {

            }
        };

        node.setId("N1");
        node.setName("Nodo Prueba");
        node.setType(TipoNodo.COMMAND);
        node.setPolitica(PoliticaError.STOP_ON_FAIL);

        assertEquals("N1", node.getId());
        assertEquals("Nodo Prueba", node.getName());
        assertEquals(TipoNodo.COMMAND, node.getType());
        assertEquals(PoliticaError.STOP_ON_FAIL, node.getErrorPolicy());
    }
}