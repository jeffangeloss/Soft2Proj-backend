package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.model.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    @Test
    void testNodeContractMutants() {
        Node node = new Node() {
            @Override
            public void execute(ExecutionContext context) { }
        };

        node.setId("ID_TEST");
        node.setName("Nombre Prueba");
        node.setType(TipoNodo.COMMAND);
        node.setPolitica(PoliticaError.STOP_ON_FAIL);

        assertEquals("ID_TEST", node.getId());
        assertEquals("Nombre Prueba", node.getName());
        assertEquals(TipoNodo.COMMAND, node.getType());
        assertEquals(PoliticaError.STOP_ON_FAIL, node.getErrorPolicy());
    }
}