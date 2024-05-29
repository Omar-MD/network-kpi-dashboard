package com.tools.dashboard.integration;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllNodes() throws Exception {
        mockMvc.perform(get("/kpi"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetNodeById() throws Exception {
        Long nodeId = 23L; // Modify according to your test data
        mockMvc.perform(get("/kpi/{id}", nodeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(nodeId.intValue())));
    }

//    @Test
//    public void testAddOrUpdateNode() throws Exception {
//        NodeData newNode = new NodeData();
//        newNode.setNodeId(22);
//        newNode.setLatency(22.75);
//        newNode.setErrorRate(0.67);
//        newNode.setThroughput(3.93);
//
//        mockMvc.perform(post("/kpi/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newNode)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nodeId", is(newNode.getNodeId())))
//                .andExpect(jsonPath("$.latency", is(newNode.getLatency())))
//                .andExpect(jsonPath("$.errorRate", is(newNode.getErrorRate())))
//                .andExpect(jsonPath("$.throughput", is(newNode.getThroughput())));
//    }

    @Test
    public void testDeleteNode() throws Exception {
        Long nodeId = 5L; // Modify according to your test data
        mockMvc.perform(delete("/kpi/remove/{id}", nodeId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("NodeData with id: " + nodeId + " removed")));
    }

    @Test
    public void testGetEntriesForNodeId() throws Exception {
        int nodeId = 1; // Modify according to your test data
        mockMvc.perform(get("/kpi/node_id_entries/{node_id}", nodeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetEntriesForNetworkId() throws Exception {
        int netId = 1; // Modify according to your test data
        mockMvc.perform(get("/kpi/network_id_entries/{net_id}", netId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

//    @Test
//    public void testGetNodeInTimeframe() throws Exception {
//        mockMvc.perform(get("/kpi/time_range")
//                        .param("start", "2024-05-28 12:36:44")
//                        .param("end", "2024-05-28 12:39:20"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
//    }

    @Test
    public void testGetNodesByLatencyRange() throws Exception {
        mockMvc.perform(get("/kpi/latency_range")
                        .param("low", "10")
                        .param("high", "100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetNodesByErrorRateRange() throws Exception {
        mockMvc.perform(get("/kpi/error_range")
                        .param("low", "0.1")
                        .param("high", "0.5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetNodesByThroughputRange() throws Exception {
        mockMvc.perform(get("/kpi/throughput_range")
                        .param("low", "1.47")
                        .param("high", "4.25"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAllLatencyTimestamps() throws Exception {
        mockMvc.perform(get("/kpi/latency_over_time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAllErrorRateTimestamps() throws Exception {
        mockMvc.perform(get("/kpi/error_rate_over_time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAllThroughputTimestamps() throws Exception {
        mockMvc.perform(get("/kpi/throughput_over_time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetLatencyErrorRateThroughput() throws Exception {
        mockMvc.perform(get("/kpi/latency_error_rate_throughput"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetErrorRateVsThroughput() throws Exception {
        mockMvc.perform(get("/kpi/error_vs_throughput"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetLatencyVsThroughput() throws Exception {
        mockMvc.perform(get("/kpi/latency_vs_throughput"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

//    @Test
//    public void testGetTopTenNodes_Latency() throws Exception {
//        mockMvc.perform(get("/kpi/top_ten_latency"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))));
//    }

    @Test
    public void testGetAvgLatencyPerNetwork() throws Exception {
        mockMvc.perform(get("/kpi/avg_latency_per_network"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAvgErrorRatePerNetwork() throws Exception {
        mockMvc.perform(get("/kpi/avg_error_rate_per_network"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAvgThroughputPerNetwork() throws Exception {
        mockMvc.perform(get("/kpi/avg_throughput_per_network"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetAverageMetrics() throws Exception {
        mockMvc.perform(get("/kpi/average_metrics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testGetNodeWithHighestMetrics() throws Exception {
        mockMvc.perform(get("/kpi/node_highest_metrics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }
}

