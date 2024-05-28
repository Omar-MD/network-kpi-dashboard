package com.tools.dashboard.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
//	@GetMapping
//	public String index() {
//		return "<h1>NodeData Dashboard</h1>";
//	}
	
	@GetMapping("/kpi")
	public Iterable<NodeData> getAllNodes(){
		return nodeRepo.findAll();
	}
	
	@RequestMapping("/kpi/{id}")
	public Optional<NodeData> getNodeById(@PathVariable("id") Long id){
		Optional<NodeData> node = nodeRepo.findById(id);
		if(node.isPresent()) {
			return node;
		}
		else {
			throw new NodeNotFoundException("No NodeData with id "+id+" found");
		}
	}
	
	@RequestMapping("/kpi/node_id_entries/{node_id}")
	public Optional<List<NodeData>> getEntriesForNodeId(@PathVariable("node_id") int node_id){
		Optional<List<NodeData>> entries = nodeRepo.findByNodeId(node_id);
		if(entries.isPresent()) {
			return entries;
		}
		else {
			throw new NodeNotFoundException("No entries with node id "+node_id+" found");
		}
	}
	
	@RequestMapping("/kpi/network_id_entries/{net_id}")
	public Optional<List<NodeData>> getEntriesForNetworkId(@PathVariable("net_id") int net_id){
		Optional<List<NodeData>> entries = nodeRepo.findByNetworkId(net_id);
		if(entries.isPresent()) {
			return entries;
		}
		else {
			throw new NodeNotFoundException("No entries with node id "+net_id+" found");
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
	
	@RequestMapping("/kpi/time_range")
	public ResponseEntity<List<NodeData>> getNodeInTimeframe(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
		List<NodeData> nodeInTimeframe = new ArrayList<>();
		nodeInTimeframe = nodeRepo.findByTimestampBetween(start, end);
		
		if(nodeInTimeframe.size() > 0) {
			return new ResponseEntity<List<NodeData>>(nodeInTimeframe, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<NodeData>>(nodeInTimeframe, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_range")
	public ResponseEntity<List<NodeData>> getNodesByLatencyRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInLatencyRange = new ArrayList<>();
		nodeInLatencyRange = nodeRepo.findByLatencyBetween(low, high);
		
		if(nodeInLatencyRange.size() > 0) {
			return new ResponseEntity<List<NodeData>>(nodeInLatencyRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<NodeData>>(nodeInLatencyRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/error_range")
	public ResponseEntity<List<NodeData>> getNodesByErrorRateRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInErrorRateRange = new ArrayList<>();
		nodeInErrorRateRange = nodeRepo.findByErrorRateBetween(low, high);
		
		if(nodeInErrorRateRange.size() > 0) {
			return new ResponseEntity<List<NodeData>>(nodeInErrorRateRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<NodeData>>(nodeInErrorRateRange, HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping("/kpi/throughput_range")
	public ResponseEntity<List<NodeData>> getNodesByThroughputRange(@RequestParam double low, @RequestParam double high){
		List<NodeData> nodeInThroughputRange = new ArrayList<>();
		nodeInThroughputRange = nodeRepo.findByThroughputBetween(low, high);
		
		if(nodeInThroughputRange.size() > 0) {
			return new ResponseEntity<List<NodeData>>(nodeInThroughputRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<NodeData>>(nodeInThroughputRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_over_time")
	public ResponseEntity<List<Object[]>> getAllLatencyTimestamps(){
		List<Object[]> latencyTimestamps = new ArrayList<>();
		latencyTimestamps = nodeRepo.findLatencyTimestamps();
		
		if(latencyTimestamps.size() > 0) {
			return new ResponseEntity<List<Object[]>>(latencyTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(latencyTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/error_rate_over_time")
	public ResponseEntity<List<Object[]>> getAllErrorRateTimestamps(){
		List<Object[]> errorRateTimestamps = new ArrayList<>();
		errorRateTimestamps = nodeRepo.findErrorRateTimestamps();

		if(errorRateTimestamps.size() > 0) {
			return new ResponseEntity<List<Object[]>>(errorRateTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(errorRateTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/throughput_over_time")
	public ResponseEntity<List<Object[]>> getAllThroughputTimestamps(){
		List<Object[]> throughputTimestamps = new ArrayList<>();
		throughputTimestamps = nodeRepo.findThroughputTimestamps();

		if(throughputTimestamps.size() > 0) {
			return new ResponseEntity<List<Object[]>>(throughputTimestamps, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(throughputTimestamps, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_error_rate_throughput")
	public ResponseEntity<List<Object[]>> getLatencyErrorRateThroughput(){
		List<Object[]> l_er_t = new ArrayList<>();
		l_er_t = nodeRepo.findAllLatencyErrorRateThroughput();	
		
		if(l_er_t.size() > 0) {
			return new ResponseEntity<List<Object[]>>(l_er_t, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(l_er_t, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/error_vs_throughput")
	public ResponseEntity<List<Object[]>> getErrorRateVsThroughput(){
		List<Object[]> errorrate_vs_throughput = new ArrayList<>();
		errorrate_vs_throughput = nodeRepo.findErrorVsThroughput();
		
		if(errorrate_vs_throughput.size() > 0) {
			return new ResponseEntity<List<Object[]>>(errorrate_vs_throughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(errorrate_vs_throughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_vs_throughput")
	public ResponseEntity<List<Object[]>> getLatencyVsThroughput(){
		List<Object[]> latency_vs_throughput = new ArrayList<>();
		latency_vs_throughput = nodeRepo.findLatencyVsThroughput();
		
		if(latency_vs_throughput.size() > 0) {
			return new ResponseEntity<List<Object[]>>(latency_vs_throughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(latency_vs_throughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/top_ten_latency")
	public ResponseEntity<List<NodeData>> getTopTenNodes_Latency(){
		List<NodeData> top10_latency = new ArrayList<>();
		top10_latency = nodeRepo.findTop10Nodes_Latency();
		
		if(top10_latency.size() > 0) {
			return new ResponseEntity<List<NodeData>>(top10_latency, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<NodeData>>(top10_latency, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/avg_latency_per_network")
	public ResponseEntity<List<Object[]>> getAvgLatencyPerNetwork(){
		List<Object[]> avg_latency_per_network = new ArrayList<>();
		avg_latency_per_network = nodeRepo.findAvgLatencyPerNetwork();
		
		if(avg_latency_per_network.size() > 0) {
			return new ResponseEntity<List<Object[]>>(avg_latency_per_network, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(avg_latency_per_network, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/avg_error_rate_per_network")
	public ResponseEntity<List<Object[]>> getAvgErrorRatePerNetwork(){
		List<Object[]> avg_error_rate_per_network = new ArrayList<>();
		avg_error_rate_per_network = nodeRepo.findAvgErrorPerNetwork();
		
		if(avg_error_rate_per_network.size() > 0) {
			return new ResponseEntity<List<Object[]>>(avg_error_rate_per_network, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(avg_error_rate_per_network, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/avg_throughput_per_network")
	public ResponseEntity<List<Object[]>> getAvgThroughputPerNetwork(){
		List<Object[]> avg_throughput_per_network = new ArrayList<>();
		avg_throughput_per_network = nodeRepo.findAvgThroughputPerNetwork();
		
		if(avg_throughput_per_network.size() > 0) {
			return new ResponseEntity<List<Object[]>>(avg_throughput_per_network, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(avg_throughput_per_network, HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping("/kpi/average_metrics")
	public ResponseEntity<List<Object[]>> getAverageMetrics() {
		List<Object[]> averagesList = nodeRepo.findAverageMetrics();
		if (averagesList != null && !averagesList.isEmpty()) {
			return ResponseEntity.ok(averagesList);
		} else {
			return ResponseEntity.noContent().build();
		}
	}


	@RequestMapping("kpi/node_highest_metrics")
	public ResponseEntity<List<Object[]>> getNodeWithHighestMetrics() {
		List<Object[]> result = nodeRepo.findNodeWithHighestMetrics();
		if (!result.isEmpty()) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or return ResponseEntity.noContent().build();
		}
	}
}
