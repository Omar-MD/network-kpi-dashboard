package com.tools.dashboard.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.tools.dashboard.dao.NodeRepository;
import com.tools.dashboard.dto.NodeData;
import com.tools.dashboard.exceptions.NodeNotFoundException;

@RestController
@Service
public class NodeController {
	@Autowired
	NodeRepository nodeRepo;
	
	@GetMapping("/kpi")
	public Iterable<NodeData> getAllNodes(){
		return nodeRepo.findAll();
	}
	
	@GetMapping("/kpi/{id}")
	public Optional<NodeData> getNodeById(@PathVariable("id") Long id){
		Optional<NodeData> node = nodeRepo.findById(id);
		if(node.isPresent()) {
			return node;
		}
		else {
			throw new NodeNotFoundException("No NodeData with id "+id);
		}
	}
	
	@GetMapping("/kpi/node_id_entries/{node_id}")
	public Optional<List<NodeData>> getEntriesForNodeId(@PathVariable("node_id") int nodeId){
		Optional<List<NodeData>> entries = nodeRepo.findByNodeId(nodeId);
		if(entries.isPresent()) {
			return entries;
		}
		else {
			throw new NodeNotFoundException("No entries found for node id "+nodeId);
		}
	}
	
	@GetMapping("/kpi/network_id_entries/{net_id}")
	public Optional<List<NodeData>> getEntriesForNetworkId(@PathVariable("net_id") int netId){
		Optional<List<NodeData>> entries = nodeRepo.findByNetworkId(netId);
		if(entries.isPresent()) {
			return entries;
		}
		else {
			throw new NodeNotFoundException("No entries with node id "+netId+" found");
		}
	}
	
	@PostMapping("/kpi/add")
	public ResponseEntity<?> addOrUpdateNode(@RequestBody NodeData node){
		try {
			NodeData savedNode = nodeRepo.save(node);
			if(savedNode!=null) {
				return ResponseEntity.ok(savedNode);
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save Node"); 
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/kpi/remove/{id}")
	public String deleteNode(@PathVariable("id") Long id) {
		Optional<NodeData> node = nodeRepo.findById(id);
		if(node.isPresent()) {
			nodeRepo.deleteById(id);
			return "NodeData with id: "+id+" removed";
		}else {
			throw new NodeNotFoundException("No NodeData with id: "+id);
		}
	}
	
	@GetMapping("/kpi/time_range")
	public ResponseEntity<List<NodeData>> getNodeInTimeframe(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
		List<NodeData> nodeInTimeframe = nodeRepo.findByTimestampBetween(start, end);
		
		if(!nodeInTimeframe.isEmpty()) {
			return new ResponseEntity<>(nodeInTimeframe, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(nodeInTimeframe, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/latency_range")
	public ResponseEntity<List<NodeData>> getNodesByLatencyRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInLatencyRange = nodeRepo.findByLatencyBetween(low, high);
		
		if(!nodeInLatencyRange.isEmpty()) {
			return new ResponseEntity<>(nodeInLatencyRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(nodeInLatencyRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/error_range")
	public ResponseEntity<List<NodeData>> getNodesByErrorRateRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInErrorRateRange = nodeRepo.findByErrorRateBetween(low, high);
		
		if(!nodeInErrorRateRange.isEmpty()) {
			return new ResponseEntity<>(nodeInErrorRateRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(nodeInErrorRateRange, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/kpi/throughput_range")
	public ResponseEntity<List<NodeData>> getNodesByThroughputRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInThroughputRange = nodeRepo.findByThroughputBetween(low, high);
		
		if(!nodeInThroughputRange.isEmpty()) {
			return new ResponseEntity<>(nodeInThroughputRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(nodeInThroughputRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/latency_over_time")
	public ResponseEntity<List<Object[]>> getAllLatencyTimestamps(){
		List<Object[]> latencyTimestamps = nodeRepo.findLatencyTimestamps();
		
		if(!latencyTimestamps.isEmpty()) {
			return new ResponseEntity<>(latencyTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(latencyTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/error_rate_over_time")
	public ResponseEntity<List<Object[]>> getAllErrorRateTimestamps(){
		List<Object[]> errorRateTimestamps = nodeRepo.findErrorRateTimestamps();

		if(!errorRateTimestamps.isEmpty()) {
			return new ResponseEntity<>(errorRateTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(errorRateTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/throughput_over_time")
	public ResponseEntity<List<Object[]>> getAllThroughputTimestamps(){
		List<Object[]> throughputTimestamps = nodeRepo.findThroughputTimestamps();

		if(!throughputTimestamps.isEmpty()) {
			return new ResponseEntity<>(throughputTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(throughputTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/latency_error_rate_throughput")
	public ResponseEntity<List<Object[]>> getLatencyErrorRateThroughput(){
		List<Object[]> latencyErrorThroughput = nodeRepo.findAllLatencyErrorRateThroughput();	
		
		if(!latencyErrorThroughput.isEmpty()) {
			return new ResponseEntity<>(latencyErrorThroughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(latencyErrorThroughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/error_vs_throughput")
	public ResponseEntity<List<Object[]>> getErrorRateVsThroughput(){
		List<Object[]> errorrateVSthroughput = nodeRepo.findErrorVsThroughput();
		
		if(!errorrateVSthroughput.isEmpty()) {
			return new ResponseEntity<>(errorrateVSthroughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(errorrateVSthroughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/latency_vs_throughput")
	public ResponseEntity<List<Object[]>> getLatencyVsThroughput(){
		List<Object[]> latencyVSthroughput = nodeRepo.findLatencyVsThroughput();
		
		if(!latencyVSthroughput.isEmpty()) {
			return new ResponseEntity<>(latencyVSthroughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(latencyVSthroughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/top_ten_latency")
	public ResponseEntity<List<NodeData>> getTopTenNodesLatency(){
		List<NodeData> top10Latency = nodeRepo.findTop10Nodes_Latency();
		
		if(!top10Latency.isEmpty()) {
			return new ResponseEntity<>(top10Latency, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(top10Latency, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/avg_latency_per_network")
	public ResponseEntity<List<Object[]>> getAvgLatencyPerNetwork(){
		List<Object[]> avgLatencyPerNetwork = nodeRepo.findAvgLatencyPerNetwork();
		
		if(!avgLatencyPerNetwork.isEmpty()) {
			return new ResponseEntity<>(avgLatencyPerNetwork, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(avgLatencyPerNetwork, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/avg_error_rate_per_network")
	public ResponseEntity<List<Object[]>> getAvgErrorRatePerNetwork(){
		List<Object[]> avgErrorRatePerNetwork = nodeRepo.findAvgErrorPerNetwork();
		
		if(!avgErrorRatePerNetwork.isEmpty()) {
			return new ResponseEntity<>(avgErrorRatePerNetwork, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(avgErrorRatePerNetwork, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/kpi/avg_throughput_per_network")
	public ResponseEntity<List<Object[]>> getAvgThroughputPerNetwork(){
		List<Object[]> avgThroughputPerNetwork = nodeRepo.findAvgThroughputPerNetwork();
		
		if(!avgThroughputPerNetwork.isEmpty()) {
			return new ResponseEntity<>(avgThroughputPerNetwork, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(avgThroughputPerNetwork, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/kpi/average_metrics")
	public ResponseEntity<List<Object[]>> getAverageMetrics() {
		List<Object[]> averagesList = nodeRepo.findAverageMetrics();
		if (averagesList != null && !averagesList.isEmpty()) {
			return ResponseEntity.ok(averagesList);
		} else {
			return ResponseEntity.noContent().build();
		}
	}


	@GetMapping("kpi/node_highest_metrics")
	public ResponseEntity<List<Object[]>> getNodeWithHighestMetrics() {
		List<Object[]> result = nodeRepo.findNodeWithHighestMetrics();
		if (!result.isEmpty()) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	@GetMapping("/kpi/node_highest_throughput")
    public ResponseEntity<List<Object[]>> getNodeWithHighestThroughput() {
        List<Object[]> result = nodeRepo.findNodeWithHighestThroughput();
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/kpi/node_highest_latency")
    public ResponseEntity<List<Object[]>> getNodeWithHighestLatency() {
        List<Object[]> result = nodeRepo.findNodeWithHighestLatency();
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/kpi/node_highest_error_rate")
    public ResponseEntity<List<Object[]>> getNodeWithHighestErrorRate() {
        List<Object[]> result = nodeRepo.findNodeWithHighestErrorRate();
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
