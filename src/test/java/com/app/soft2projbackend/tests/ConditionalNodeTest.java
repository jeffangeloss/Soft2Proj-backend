package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.nodetypes.ConditionalNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConditionalNodeTest {

    @Mock
    private ExecutionContext context;
    @Mock
    private Flow flow;
    @Mock
    private Node targetNode;

    @InjectMocks
    private ConditionalNode conditionalNode;

    @Test
    void killConditionalMutants() {
        conditionalNode.setTarget("T1");
        when(context.getFlow()).thenReturn(flow);
        when(flow.getNodeById("T1")).thenReturn(targetNode);
        when(targetNode.getId()).thenReturn("T1");

        Variable var = new Variable("CONDITIONRESULTT1", true);
        when(context.getVariableList()).thenReturn(List.of(var));

        conditionalNode.execute(context);

        assertTrue(conditionalNode.isCondition(), "ERROR: Mutante invirtió la decisión lógica");
    }
}
