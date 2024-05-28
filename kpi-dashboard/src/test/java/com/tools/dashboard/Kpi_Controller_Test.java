package com.tools.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class Kpi_Controller_Test {
	@Mock
	private NodeRepository nodeRepo;
	
	@InjectMocks
	private NodeController nc;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getAllKpi_Test() {
		List<NodeData> kpiList = createNodeIterable();
		when(nodeRepo.findAll()).thenReturn(kpiList);
		
		Iterable<NodeData> kpis = nc.getAllNodes();
		assertEquals(kpiList, kpis);
	}
	
	@Test
	public void getNodeById_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.findById(node1.getId())).thenReturn(Optional.of(node1));
		
		Optional<NodeData> nodeById = nc.getNodeById(node1.getId());
		assertTrue(nodeById.isPresent());
		assertEquals(node1, nodeById.get());
	}
	
	@Test
	public void getKpiById_IdNotFound_Test() {
		when(nodeRepo.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(NodeNotFoundException.class, () ->{
			nc.getNodeById(999L);
		});
	}

	
	@Test
	public void addOrUpdateKpi_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.save(node1)).thenReturn(node1);
		
        ResponseEntity<?> responseEntity = nc.addOrUpdateNode(node1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(node1, responseEntity.getBody());
	}
	
	@Test
	void AddOrUpdateKpi_FailedToSave_Test() {
	    when(nodeRepo.save(any(NodeData.class))).thenThrow(new RuntimeException());

	    ResponseEntity<?> responseEntity = nc.addOrUpdateNode(new NodeData());

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("An error occurred: null", responseEntity.getBody());
	}
	
	@Test
	void testAddOrUpdateKpi_AnErrorOccurred() {
	    ResponseEntity<?> responseEntity = nc.addOrUpdateNode(null);

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("Failed to save Node", responseEntity.getBody());
	}
	
	@Test
	public void deleteKpi_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData kpi1 = new NodeData(1,100,20.3,180,0.02, dateTime);
		when(nodeRepo.findById(1L)).thenReturn(Optional.of(kpi1));

        String result = nc.deleteNode(1L);

        verify(nodeRepo, times(1)).deleteById(1L);

        assertEquals("NodeData with id: 1 removed", result);
	}
	
	@Test
	public void deleteKpi_IdNotFound_Test() {
		when(nodeRepo.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(NodeNotFoundException.class, () ->{
			nc.deleteNode(999L);
		});
	}
	
	
//	@Test
//	public void getKpiInTimeframe_Test() {
//		LocalDateTime dateTimeLow = LocalDateTime.of(2024, 5, 25, 10, 22, 22, 412000000);
//		LocalDateTime dateTimeHigh = LocalDateTime.of(2024, 5, 27, 10, 22, 22, 412000000);
//		NodeData kpi1 = new NodeData(1,100,20.3,180,0.02, dateTimeLow);
//		NodeData kpi2 = new NodeData(2,200,18.5,210,0.018, dateTimeHigh);
//		List<NodeData> kpiInRange = new ArrayList<>();
//		kpiInRange.add(kpi1);
//		kpiInRange.add(kpi2);
//		List<NodeData> kpis = createKpiIterable();
//		kpis.addAll(kpiInRange);
//		
//		when(nodeRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh)).thenReturn(kpiInRange);
//		
//		assertEquals(kpiInRange, nodeRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh));
//	}
	
	@Test
    void getKpiInTimeframe_WithData_Test() {
        List<NodeData> kpiList = createNodeIterable();
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        when(nodeRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(kpiList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodeInTimeframe(start, end);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(nodeRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getKpiInTimeframe_EmptyData_Test() {
        when(nodeRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        ResponseEntity<List<NodeData>> responseEntity = nc.getNodeInTimeframe(start, end);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

	
    @Test
    void GetKpiByLatencyRange_WithData_Test() {
        List<NodeData> kpiList = createNodeIterable();
        
        when(nodeRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(nodeRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

    @Test
    void GetKpiByLatencyRange_EmptyData_Test() {
        when(nodeRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

	
	@Test
    void getKpiByErrorRateRange_WithData_Test() {
        List<NodeData> kpiList = createNodeIterable();
        
        when(nodeRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(nodeRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }

    @Test
    void getKpiByErrorRateRange_EmptyData_Test() {
        when(nodeRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }
	
	@Test
	public void getKpiByThroughputRange_Test() {
		List<NodeData> kpis = createNodeIterable();
		List<NodeData> inRange = kpis.subList(2, kpis.size());
		when(nodeRepo.findByThroughputBetween(0.02, 0.03)).thenReturn(inRange);
		
		assertEquals(inRange, nodeRepo.findByThroughputBetween(0.02, 0.03));
	}
	
	@Test
	 void getKpiByThroughputRange_WithData_Test() {
        List<NodeData> kpiList = createNodeIterable();
        
        when(nodeRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(nodeRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }

    @Test
    void getKpiByThroughputRange_EmptyData_Test() {
        when(nodeRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<NodeData>> responseEntity = nc.getNodesByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(nodeRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }

	public List<NodeData> createNodeIterable(){
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
