package com.app.soft2projbackend.tests;


import com.app.soft2projbackend.exceptions.InvalidStartException;
import com.app.soft2projbackend.handlers.Validator;
import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.nodetypes.HttpRequestNode;
import com.app.soft2projbackend.nodetypes.StartNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowValidatorTest {

    @Test
    public void StartNodeDontExist(){
        Flow flow = new Flow();
        flow.setNodes(List.of(
                new HttpRequestNode()
        ));

        Validator validator = new Validator();



        InvalidStartException exception = assertThrows(
                InvalidStartException.class,
                () -> validator.validate(flow)
        );

        assertEquals("Numero de Nodos Start no válido", exception.getMessage());
    }

    @Test
    public void StartNodeExist(){
        Flow flow = new Flow();
        flow.setNodes(List.of(
                new StartNode()
        ));

        Validator validator = new Validator();

        assertDoesNotThrow(() -> validator.validate(flow));
    }

    @Test
    public void MultipleStartNode(){
        Flow flow = new Flow();
        flow.setNodes(List.of(
                new StartNode(),
                new StartNode()
        ));

        Validator validator = new Validator();



        InvalidStartException exception = assertThrows(
                InvalidStartException.class,
                () -> validator.validate(flow)
        );

        assertEquals("Numero de Nodos Start no válido", exception.getMessage());
    }
}
