package com.tools.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.dashboard.dto.NodeData;

public class NodeData_DTO_Test {
	NodeData nd;
	
	@BeforeEach
	void setUp() {
		nd = new NodeData();
	}
	
//	@Test
//	void testNodeId() {
//		nd.setNetworkId(15);
//		assertEquals(15, nd.getId());
//	}
	
	@Test
	void testNetId() {
		nd.setNetworkId(15);
		assertEquals(15, nd.getNetworkId());
	}
	
	@Test
	void testLatency() {
		nd.setLatency(20.3);
		assertEquals(20.3, nd.getLatency());
	}
	
	@Test
	void testThroughput() {
		nd.setThroughput(180);
		assertEquals(180, nd.getThroughput());
	}
	
	@Test
	void testErrorRate() {
		nd.setErrorRate(0.02);
		assertEquals(0.02, nd.getErrorRate());
	}
	
	@Test
	void testTimestamp() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		nd.setTimestamp(dateTime);
		assertEquals(dateTime, nd.getTimestamp());
	}
	
	@Test
	void testContructor() {
		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 24, 10, 22, 22, 412000000);
		NodeData node = new NodeData(1,1,20.3,180,0.02, dateTime);
		assertEquals(1, node.getNodeId());
	}
}
