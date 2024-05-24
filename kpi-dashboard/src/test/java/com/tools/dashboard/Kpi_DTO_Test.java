package com.tools.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.dashboard.dto.Kpi;

public class Kpi_DTO_Test {
	Kpi kpi;
	
	@BeforeEach
	void setUp() {
		kpi = new Kpi();
	}
	
	@Test
	void testNodeId() {
		kpi.setNode_id(15);
		assertEquals(15, kpi.getNode_id());
	}
	
	@Test
	void testNetId() {
		kpi.setNet_id(15);
		assertEquals(15, kpi.getNet_id());
	}
	
	@Test
	void testLatency() {
		kpi.setLatency(20.3);
		assertEquals(20.3, kpi.getLatency());
	}
	
	@Test
	void testThroughput() {
		kpi.setThroughput(180);
		assertEquals(180, kpi.getThroughput());
	}
	
	@Test
	void testErrorRate() {
		kpi.setError_rate(0.02);
		assertEquals(0.02, kpi.getError_rate());
	}
	
	@Test
	void testTimestamp() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		kpi.setTimestamp(dateTime);
		assertEquals(dateTime, kpi.getTimestamp());
	}
	
	@Test
	void testContructor() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		kpi = new Kpi(1,1,20.3,180,0.02, dateTime);
		assertEquals(1, kpi.getNode_id());
	}
}
