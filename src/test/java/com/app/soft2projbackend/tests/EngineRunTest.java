package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.handlers.Engine;
import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngineRunTest {
    @InjectMocks
    private Engine engine;

    @Mock
    private Flow flow;

    @Mock
    private Node startNode;

    @Mock
    private Node nextNode;

    @Test
    public void RunSuccesfull() throws Exception {

        when(flow.getStartNode()).thenReturn(startNode);

        Engine spyEngine = Mockito.spy(engine);

        doReturn(nextNode)
                .doReturn(null)
                .when(spyEngine)
                .getNextNode(any(), any(), any());


        ExecutionContext context = spyEngine.run(flow);

        verify(startNode).execute(any());
        verify(nextNode).execute(any());

        assertNotNull(context);
    }

    @Test
    void StopExecutionWhenNodeFails() throws Exception {

        when(flow.getStartNode()).thenReturn(startNode);
        when(startNode.getErrorPolicy()).thenReturn(PoliticaError.STOP_ON_FAIL);

        doThrow(new RuntimeException("Error"))
                .when(startNode).execute(any());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> engine.run(flow)
        );

        assertNotNull(exception);
    }

    @Test
    void ContinueExecutionWhenNodedFails() throws Exception {

        when(flow.getStartNode()).thenReturn(startNode);
        when(startNode.getErrorPolicy())
                .thenReturn(PoliticaError.CONTINUE_ON_FAIL);

        doThrow(new RuntimeException("Error"))
                .when(startNode).execute(any());

        Engine spyEngine = Mockito.spy(engine);

        doReturn(nextNode)
                .doReturn(null)
                .when(spyEngine)
                .getNextNode(any(), any(), any());

        ExecutionContext context = spyEngine.run(flow);

        verify(nextNode).execute(any());
        assertNotNull(context);
    }
}
