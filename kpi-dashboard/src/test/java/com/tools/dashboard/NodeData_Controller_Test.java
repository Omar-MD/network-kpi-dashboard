package com.tools.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tools.dashboard.controllers.NodeController;
import com.tools.dashboard.dao.NodeRepository;
import com.tools.dashboard.dto.NodeData;
import com.tools.dashboard.exceptions.NodeNotFoundException;

class NodeData_Controller_Test {
	@Mock
	private NodeRepository nodeRepo;
	
	@InjectMocks
	private NodeController nc;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void getAllNodes_Test() {
		List<NodeData> nodeList = createNodeIterable();
		when(nodeRepo.findAll()).thenReturn(nodeList);
		
		Iterable<NodeData> nodes = nc.getAllNodes();
		assertEquals(nodeList, nodes);
	}
	
	@Test
	void getNodeById_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.findById(node1.getId())).thenReturn(Optional.of(node1));
		
		Optional<NodeData> nodeById = nc.getNodeById(node1.getId());
		assertTrue(nodeById.isPresent());
		assertEquals(node1, nodeById.get());
	}
	
	@Test
	void getNodeById_IdNotFound_Test() {
		when(nodeRepo.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(NodeNotFoundException.class, () ->{
			nc.getNodeById(999L);
		});
	}

	@Test
	void getEntriesForNodeId_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		Optional<List<NodeData>> nodes = Optional.of(createNodeIterable());
		when(nodeRepo.findByNodeId(node1.getNodeId())).thenReturn(nodes);
		
		Optional<List<NodeData>> nodeById = nc.getEntriesForNodeId(node1.getNodeId());
		assertTrue(nodeById.isPresent());
	}
	
	@Test
	void getEntriesForNodeId_IdNotFound_Test() {
	    when(nodeRepo.findByNodeId(999)).thenReturn(Optional.empty());

	    assertThrows(NodeNotFoundException.class, () -> {
	        nc.getEntriesForNodeId(999);
	    });
	}

	@Test
	void getEntriesForNetworkId_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		Optional<List<NodeData>> nodes = Optional.of(createNodeIterable());
		when(nodeRepo.findByNetworkId(node1.getNodeId())).thenReturn(nodes);
		
		Optional<List<NodeData>> nodeById = nc.getEntriesForNetworkId(node1.getNodeId());
		assertTrue(nodeById.isPresent());
	}
	
	@Test
	void getEntriesForNetworkId_IdNotFound_Test() {
	    when(nodeRepo.findByNetworkId(999)).thenReturn(Optional.empty());

	    assertThrows(NodeNotFoundException.class, () -> {
	        nc.getEntriesForNetworkId(999);
	    });
	}
	
	@Test
	void addOrUpdateNode_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.save(node1)).thenReturn(node1);
		
        ResponseEntity<?> responseEntity = nc.addOrUpdateNode(node1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(node1, responseEntity.getBody());
	}
	
	@Test
	void AddOrUpdateNode_AnErrorOccurred_Test() {
	    when(nodeRepo.save(any(NodeData.class))).thenThrow(new RuntimeException());

	    ResponseEntity<?> responseEntity = nc.addOrUpdateNode(new NodeData());

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("An error occurred: null", responseEntity.getBody());
	}
	
	@Test
	void testAddOrUpdateNode_FailedToSave_Test() {
	    ResponseEntity<?> responseEntity = nc.addOrUpdateNode(null);

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("Failed to save Node", responseEntity.getBody());
	}
	
	@Test
	void deleteNode_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.findById(1L)).thenReturn(Optional.of(node1));

        String result = nc.deleteNode(1L);

        verify(nodeRepo, times(1)).deleteById(1L);

        assertEquals("NodeData with id: 1 removed", result);
	}
	
	@Test
	void deleteNode_IdNotFound_Test() {
		when(nodeRepo.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(NodeNotFoundException.class, () ->{
			nc.deleteNode(999L);
		});
	}
	
	
	@Test
	void getnodeInTimeframe_Test() {
		LocalDateTime dateTimeLow = LocalDateTime.of(2024, 5, 25, 10, 22, 22, 412000000);
		LocalDateTime dateTimeHigh = LocalDateTime.of(2024, 5, 27, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTimeLow);
		NodeData node2 = new NodeData(2,200,18.5,210,0.018, dateTimeHigh);
		List<NodeData> nodeInRange = new ArrayList<>();
		nodeInRange.add(node1);
		nodeInRange.add(node2);
		List<NodeData> nodes = createNodeIterable();
		nodes.addAll(nodeInRange);
		
		when(nodeRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh)).thenReturn(nodeInRange);
		
		assertEquals(nodeInRange, nodeRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh));
	}
	
	@Test
    void getNodeInTimeframe_WithData_Test() {
        List<NodeData> nodeList = createNodeIterable();
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        when(nodeRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(nodeList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodeInTimeframe(start, end);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nodeList, responseEntity.getBody());
        verify(nodeRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getNodeInTimeframe_EmptyData_Test() {
        when(nodeRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        ResponseEntity<List<NodeData>> responseEntity = nc.getNodeInTimeframe(start, end);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

	
    @Test
    void GetNodeByLatencyRange_WithData_Test() {
        List<NodeData> nodeList = createNodeIterable();
        
        when(nodeRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(nodeList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nodeList, responseEntity.getBody());
        verify(nodeRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

    @Test
    void GetNodeByLatencyRange_EmptyData_Test() {
        when(nodeRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

	
	@Test
    void getByErrorRateRange_WithData_Test() {
        List<NodeData> nodeList = createNodeIterable();
        
        when(nodeRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(nodeList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nodeList, responseEntity.getBody());
        verify(nodeRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }

    @Test
    void getNodeByErrorRateRange_EmptyData_Test() {
        when(nodeRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }
	
	@Test
	void getNodeByThroughputRange_Test() {
		List<NodeData> nodes = createNodeIterable();
		List<NodeData> inRange = nodes.subList(2, nodes.size());
		when(nodeRepo.findByThroughputBetween(0.02, 0.03)).thenReturn(inRange);
		
		assertEquals(inRange, nodeRepo.findByThroughputBetween(0.02, 0.03));
	}
	
	@Test
	 void getNodeByThroughputRange_WithData_Test() {
        List<NodeData> nodeList = createNodeIterable();
        
        when(nodeRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(nodeList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nodeList, responseEntity.getBody());
        verify(nodeRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }

    @Test
    void getNodeByThroughputRange_EmptyData_Test() {
        when(nodeRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }
    
    @Test
    void getAllLatencyTimestamps_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, "2024-05-28T15:33:04"});
        mockData.add(new Object[]{20.7, "2024-05-28T15:34:04"});
        
        when(nodeRepo.findLatencyTimestamps()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAllLatencyTimestamps();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAllLatencyTimestamps_EmptyData_Test() {
        when(nodeRepo.findLatencyTimestamps()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAllLatencyTimestamps();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }

    @Test
    void getAllErrorRateTimestamps_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, "2024-05-28T15:33:04"});
        mockData.add(new Object[]{20.7, "2024-05-28T15:34:04"});
        
        when(nodeRepo.findErrorRateTimestamps()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAllErrorRateTimestamps();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAllErrorRateTimestamps_EmptyData_Test() {
        when(nodeRepo.findErrorRateTimestamps()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAllErrorRateTimestamps();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }

    @Test
    void getAllThoughputTimestamps_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, "2024-05-28T15:33:04"});
        mockData.add(new Object[]{20.7, "2024-05-28T15:34:04"});
        
        when(nodeRepo.findThroughputTimestamps()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAllThroughputTimestamps();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAllThroughputTimestampss_EmptyData_Test() {
        when(nodeRepo.findThroughputTimestamps()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAllThroughputTimestamps();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getLatencyErrorRateThroughput_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, 23.2, 0.02});
        mockData.add(new Object[]{20.7, 2.1, 0.1});
        
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getLatencyErrorRateThroughput();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getLatencyErrorRateThroughput_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getLatencyErrorRateThroughput();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getErrorRateVsThroughput_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, 23.2});
        mockData.add(new Object[]{20.7, 2.1});
        
        when(nodeRepo.findErrorVsThroughput()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getErrorRateVsThroughput();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getErrorRateVsThroughput_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getErrorRateVsThroughput();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getLatencyVsThroughput_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{10.5, 23.2});
        mockData.add(new Object[]{20.7, 2.1});
        
        when(nodeRepo.findLatencyVsThroughput()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getLatencyVsThroughput();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getLatencyVsThroughput_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getLatencyVsThroughput();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getTopTenNodes_Latency_WithData_Test() {
    	List<NodeData> mockData = createNodeIterable();
        
        when(nodeRepo.findTop10Nodes_Latency()).thenReturn(mockData);
        
        ResponseEntity<List<NodeData>> responseEntity = nc.getTopTenNodesLatency();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getTopTenNodes_Latency_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getTopTenNodesLatency();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getAvgLatencyPerNetwork_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, 23.2});
        mockData.add(new Object[]{2, 2.1});
        mockData.add(new Object[]{3, 23.2});
        mockData.add(new Object[]{4, 2.1});
        
        when(nodeRepo.findAvgLatencyPerNetwork()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgLatencyPerNetwork();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAvgLatencyPerNetwork_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgLatencyPerNetwork();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getAvgErrorRatePerNetwork_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, 23.2});
        mockData.add(new Object[]{2, 2.1});
        mockData.add(new Object[]{3, 23.2});
        mockData.add(new Object[]{4, 2.1});
        
        when(nodeRepo.findAvgErrorPerNetwork()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgErrorRatePerNetwork();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAvgErrorRatePerNetwork_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgErrorRatePerNetwork();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getAvgThroughputPerNetwork_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, 23.2});
        mockData.add(new Object[]{2, 2.1});
        mockData.add(new Object[]{3, 23.2});
        mockData.add(new Object[]{4, 2.1});
        
        when(nodeRepo.findAvgThroughputPerNetwork()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgThroughputPerNetwork();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAvgThroughputPerNetwork_EmptyData_Test() {
        when(nodeRepo.findAllLatencyErrorRateThroughput()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAvgThroughputPerNetwork();
        
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
    }
    
    @Test
    void getAverageMetrics_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1, 23.2});
        mockData.add(new Object[]{2, 2.1});
        mockData.add(new Object[]{3, 23.2});
        mockData.add(new Object[]{4, 2.1});
        
        when(nodeRepo.findAverageMetrics()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getAverageMetrics();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getAverageMetrics_EmptyData_Test() {
        when(nodeRepo.findAverageMetrics()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getAverageMetrics();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
    @Test
    void getAverageMetrics_Null_Test() {
        when(nodeRepo.findAverageMetrics()).thenReturn(null);

        ResponseEntity<List<Object[]>> responseEntity = nc.getAverageMetrics();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
    @Test
    void getNodeWithHighestMetrics_WithData_Test() {
    	List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{0, 23.2});
        mockData.add(new Object[]{1, 2.1});
        mockData.add(new Object[]{2, 43.9});
        mockData.add(new Object[]{3, 2.1});
        
        when(nodeRepo.findNodeWithHighestMetrics()).thenReturn(mockData);
        
        ResponseEntity<List<Object[]>> responseEntity = nc.getNodeWithHighestMetrics();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockData, responseEntity.getBody());
    }
    
    @Test
    void getNodeWithHighestMetrics_EmptyData_Test() {
        when(nodeRepo.findAverageMetrics()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Object[]>> responseEntity = nc.getNodeWithHighestMetrics();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
	List<NodeData> createNodeIterable(){
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData nd1 = new NodeData(1,100,20.3,180,0.018, dateTime);
		NodeData nd2 = new NodeData(2,200,18.5,210,0.033, dateTime);
		NodeData nd3 = new NodeData(3,300,19.1,195,0.02, dateTime);

		List<NodeData> nodes = new ArrayList<>();
		nodes.add(nd1);
		nodes.add(nd2);
		nodes.add(nd3);
		
		return nodes;
	}
}
