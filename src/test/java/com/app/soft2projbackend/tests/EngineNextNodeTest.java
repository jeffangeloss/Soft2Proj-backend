package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.handlers.Engine;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.nodetypes.ConditionalNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngineNextNodeTest {
    @InjectMocks
    private Engine engine;

    @Mock
    private Flow flow;

    @Mock
    private ExecutionContext context;

    @Mock
    private Node current;

    @Mock
    private Node nextNode;

    @Mock
    private Connection connection;

    @Test
    void NoConnections() {

        when(flow.getConnectionsFrom(current))
                .thenReturn(List.of());

        Node result = engine.getNextNode(flow, current, context);

        assertNull(result);
        verify(context).setFlow(null);
    }

    @Test
    void NotConditional() {

        when(current.getType()).thenReturn(TipoNodo.START);

        when(connection.getToNode()).thenReturn(nextNode);

        when(flow.getConnectionsFrom(current))
                .thenReturn(List.of(connection));

        Node result = engine.getNextNode(flow, current, context);

        assertEquals(nextNode, result);
    }

    @Test
    void TrueBranch() {

        ConditionalNode conditional = mock(ConditionalNode.class);

        Connection trueConn = mock(Connection.class);
        Connection falseConn = mock(Connection.class);

        Node trueNode = mock(Node.class);

        when(conditional.getType()).thenReturn(TipoNodo.CONDITIONAL);
        when(conditional.isCondition()).thenReturn(true);

        when(trueConn.isCondition()).thenReturn(true);
        when(trueConn.getToNode()).thenReturn(trueNode);

        when(falseConn.isCondition()).thenReturn(false);

        when(flow.getConnectionsFrom(conditional))
                .thenReturn(List.of(trueConn, falseConn));

        Node result = engine.getNextNode(flow, conditional, context);

        assertEquals(trueNode, result);
    }

    @Test
    void FalseBranch() {

        ConditionalNode conditional = mock(ConditionalNode.class);

        Connection trueConn = mock(Connection.class);
        Connection falseConn = mock(Connection.class);

        Node falseNode = mock(Node.class);

        when(conditional.getType()).thenReturn(TipoNodo.CONDITIONAL);
        when(conditional.isCondition()).thenReturn(false);

        when(trueConn.isCondition()).thenReturn(true);

        when(falseConn.isCondition()).thenReturn(false);
        when(falseConn.getToNode()).thenReturn(falseNode);

        when(flow.getConnectionsFrom(conditional))
                .thenReturn(List.of(trueConn, falseConn));

        Node result = engine.getNextNode(flow, conditional, context);

        assertEquals(falseNode, result);
    }

    @Test
    void NoMatchingConditionalConnection() {

        ConditionalNode conditional = mock(ConditionalNode.class);

        Connection conn = mock(Connection.class);

        when(conditional.getType()).thenReturn(TipoNodo.CONDITIONAL);
        when(conditional.isCondition()).thenReturn(true);

        when(conn.isCondition()).thenReturn(false);

        when(flow.getConnectionsFrom(conditional))
                .thenReturn(List.of(conn));

        assertThrows(NullPointerException.class,
                () -> engine.getNextNode(flow, conditional, context));
    }
}
