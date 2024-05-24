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

import com.tools.dashboard.controllers.KpiController;
import com.tools.dashboard.dao.KpiRepository;
import com.tools.dashboard.dto.Kpi;
import com.tools.dashboard.exceptions.KpiNotFoundException;

public class Kpi_Controller_Test {
	@Mock
	private KpiRepository kpiRepo;
	
	@InjectMocks
	private KpiController kc;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getAllKpi_Test() {
		List<Kpi> kpiList = createKpiIterable();
		when(kpiRepo.findAll()).thenReturn(kpiList);
		
		Iterable<Kpi> kpis = kc.getAllKpis();
		assertEquals(kpiList, kpis);
	}
	
	@Test
	public void getKpiById_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		Kpi kpi1 = new Kpi(1,100,20.3,180,0.02, dateTime);
		when(kpiRepo.findById(kpi1.getNode_id())).thenReturn(Optional.of(kpi1));
		
		Optional<Kpi> kpiById = kc.getKpiById(kpi1.getNode_id());
		assertTrue(kpiById.isPresent());
		assertEquals(kpi1, kpiById.get());
	}
	
	@Test
	public void getKpiById_IdNotFound_Test() {
		when(kpiRepo.findById(999)).thenReturn(Optional.empty());
		
		assertThrows(KpiNotFoundException.class, () ->{
			kc.getKpiById(999);
		});
	}

	
	@Test
	public void addOrUpdateKpi_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		Kpi kpi1 = new Kpi(1,100,20.3,180,0.02, dateTime);
		when(kpiRepo.save(kpi1)).thenReturn(kpi1);
		
        ResponseEntity<?> responseEntity = kc.addOrUpdateKpi(kpi1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpi1, responseEntity.getBody());
	}
	
	@Test
	void AddOrUpdateKpi_FailedToSave_Test() {
	    when(kpiRepo.save(any(Kpi.class))).thenThrow(new RuntimeException());

	    ResponseEntity<?> responseEntity = kc.addOrUpdateKpi(new Kpi());

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("An error occurred: null", responseEntity.getBody());
	}
	
	@Test
	void testAddOrUpdateKpi_AnErrorOccurred() {
	    ResponseEntity<?> responseEntity = kc.addOrUpdateKpi(null);

	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    assertEquals("Failed to save KPI", responseEntity.getBody());
	}
	
	@Test
	public void deleteKpi_Test() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		Kpi kpi1 = new Kpi(1,100,20.3,180,0.02, dateTime);
		when(kpiRepo.findById(1)).thenReturn(Optional.of(kpi1));

        String result = kc.deleteKpi(1);

        verify(kpiRepo, times(1)).deleteById(1);

        assertEquals("Kpi with id: 1 removed", result);
	}
	
	@Test
	public void deleteKpi_IdNotFound_Test() {
		when(kpiRepo.findById(999)).thenReturn(Optional.empty());
		
		assertThrows(KpiNotFoundException.class, () ->{
			kc.deleteKpi(999);
		});
	}
	
	
//	@Test
//	public void getKpiInTimeframe_Test() {
//		LocalDateTime dateTimeLow = LocalDateTime.of(2024, 5, 25, 10, 22, 22, 412000000);
//		LocalDateTime dateTimeHigh = LocalDateTime.of(2024, 5, 27, 10, 22, 22, 412000000);
//		Kpi kpi1 = new Kpi(1,100,20.3,180,0.02, dateTimeLow);
//		Kpi kpi2 = new Kpi(2,200,18.5,210,0.018, dateTimeHigh);
//		List<Kpi> kpiInRange = new ArrayList<>();
//		kpiInRange.add(kpi1);
//		kpiInRange.add(kpi2);
//		List<Kpi> kpis = createKpiIterable();
//		kpis.addAll(kpiInRange);
//		
//		when(kpiRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh)).thenReturn(kpiInRange);
//		
//		assertEquals(kpiInRange, kpiRepo.findByTimestampBetween(dateTimeLow, dateTimeHigh));
//	}
	
	@Test
    void getKpiInTimeframe_WithData_Test() {
        List<Kpi> kpiList = createKpiIterable();
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        when(kpiRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(kpiList);

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiInTimeframe(start, end);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(kpiRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void getKpiInTimeframe_EmptyData_Test() {
        when(kpiRepo.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiInTimeframe(start, end);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(kpiRepo).findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

	
    @Test
    void GetKpiByLatencyRange_WithData_Test() {
        List<Kpi> kpiList = createKpiIterable();
        
        when(kpiRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(kpiRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

    @Test
    void GetKpiByLatencyRange_EmptyData_Test() {
        when(kpiRepo.findByLatencyBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByLatencyRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(kpiRepo).findByLatencyBetween(anyDouble(), anyDouble());
    }

	
	@Test
    void getKpiByErrorRateRange_WithData_Test() {
        List<Kpi> kpiList = createKpiIterable();
        
        when(kpiRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(kpiRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }

    @Test
    void getKpiByErrorRateRange_EmptyData_Test() {
        when(kpiRepo.findByErrorRateBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByErrorRateRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(kpiRepo).findByErrorRateBetween(anyDouble(), anyDouble());
    }
	
	@Test
	public void getKpiByThroughputRange_Test() {
		List<Kpi> kpis = createKpiIterable();
		List<Kpi> inRange = kpis.subList(2, kpis.size());
		when(kpiRepo.findByThroughputBetween(0.02, 0.03)).thenReturn(inRange);
		
		assertEquals(inRange, kpiRepo.findByThroughputBetween(0.02, 0.03));
	}
	
	@Test
	 void getKpiByThroughputRange_WithData_Test() {
        List<Kpi> kpiList = createKpiIterable();
        
        when(kpiRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(kpiList);

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kpiList, responseEntity.getBody());
        verify(kpiRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }

    @Test
    void getKpiByThroughputRange_EmptyData_Test() {
        when(kpiRepo.findByThroughputBetween(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Kpi>> responseEntity = kc.getKpiByThroughputRange(0.0, 10.0);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(kpiRepo).findByThroughputBetween(anyDouble(), anyDouble());
    }

	public List<Kpi> createKpiIterable(){
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		Kpi kpi1 = new Kpi(1,100,20.3,180,0.018, dateTime);
		Kpi kpi2 = new Kpi(2,200,18.5,210,0.033, dateTime);
		Kpi kpi3 = new Kpi(3,300,19.1,195,0.02, dateTime);

		List<Kpi> kpis = new ArrayList<>();
		kpis.add(kpi1);
		kpis.add(kpi2);
		kpis.add(kpi3);
		
		return kpis;
	}
}
