package com.app.soft2projbackend.tests;

import com.app.soft2projbackend.model.Connection;
import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.model.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlowGetConnectionsTest {
    @Mock
    private Node nodeA;

    @Mock
    private Node nodeB;

    @Mock
    private Node nodeC;

    @Mock
    private Connection connection1;

    @Mock
    private Connection connection2;

    @Mock
    private Connection connection3;

    private Flow flow;

    @BeforeEach
    void setUp() {
        flow = new Flow();
    }

    @Test
    void ReturnConnectionsFromGivenNode() {

        when(connection1.getFromNode()).thenReturn(nodeA);
        when(connection2.getFromNode()).thenReturn(nodeA);
        when(connection3.getFromNode()).thenReturn(nodeB);

        flow.setConnections(List.of(connection1, connection2, connection3));

        List<Connection> result = flow.getConnectionsFrom(nodeA);

        assertEquals(2, result.size());
        assertTrue(result.contains(connection1));
        assertTrue(result.contains(connection2));
    }

    @Test
    void NodeHasNoOutgoingConnections() {

        when(connection1.getFromNode()).thenReturn(nodeB);

        flow.setConnections(List.of(connection1));

        List<Connection> result = flow.getConnectionsFrom(nodeA);

        assertTrue(result.isEmpty());
    }

    @Test
    void ReturnOnlyConnectionsFromSpecificNode() {

        when(connection1.getFromNode()).thenReturn(nodeA);
        when(connection2.getFromNode()).thenReturn(nodeB);
        when(connection3.getFromNode()).thenReturn(nodeC);

        flow.setConnections(List.of(connection1, connection2, connection3));

        List<Connection> result = flow.getConnectionsFrom(nodeB);

        assertEquals(1, result.size());
        assertEquals(connection2, result.get(0));
    }

}
