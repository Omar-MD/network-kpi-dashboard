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

import com.tools.dashboard.dao.KpiRepository;
import com.tools.dashboard.dto.Kpi;
import com.tools.dashboard.exceptions.KpiNotFoundException;

@RestController
@Service
public class KpiController {
	@Autowired
	KpiRepository kpiRepo;
	
//	@GetMapping
//	public String index() {
//		return "<h1>KPI Dashboard</h1>";
//	}
	
	@GetMapping("/kpi")
	public Iterable<Kpi> getAllKpis(){
		return kpiRepo.findAll();
	}
	
	@RequestMapping("/kpi/{id}")
	public Optional<Kpi> getKpiById(@PathVariable("id") int id){
		Optional<Kpi> kpi = kpiRepo.findById(id);
		if(kpi.isPresent()) {
			return kpi;
		}
		else {
			throw new KpiNotFoundException("No KPI with id "+id+" found");
		}
	}
	
	@PostMapping("/kpi/add")
	public ResponseEntity<?> addOrUpdateKpi(@RequestBody Kpi kpi){
		try {
			Kpi savedKpi = kpiRepo.save(kpi);
			if(savedKpi!=null) {
				return ResponseEntity.ok(savedKpi);
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save KPI"); 
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/kpi/remove/{id}")
	public String deleteKpi(@PathVariable("id") int id) {
		Optional<Kpi> kpi = kpiRepo.findById(id);
		if(kpi.isPresent()) {
			kpiRepo.deleteById(id);
			return "Kpi with id: "+id+" removed";
		}else {
			throw new KpiNotFoundException("No KPI with id: "+id);
		}
	}
	
	@RequestMapping("/kpi/time_range")
	public ResponseEntity<List<Kpi>> getKpiInTimeframe(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
		List<Kpi> kpiInTimeframe = new ArrayList<>();
		kpiInTimeframe = kpiRepo.findByTimestampBetween(start, end);
		
		if(kpiInTimeframe.size() > 0) {
			return new ResponseEntity<List<Kpi>>(kpiInTimeframe, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Kpi>>(kpiInTimeframe, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_range")
	public ResponseEntity<List<Kpi>> getKpiByLatencyRange(@RequestParam double low, @RequestParam double high){
		List<Kpi> kpiInLatencyRange = new ArrayList<>();
		kpiInLatencyRange = kpiRepo.findByLatencyBetween(low, high);
		
		if(kpiInLatencyRange.size() > 0) {
			return new ResponseEntity<List<Kpi>>(kpiInLatencyRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Kpi>>(kpiInLatencyRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/error_range")
	public ResponseEntity<List<Kpi>> getKpiByErrorRateRange(@RequestParam double low, @RequestParam double high){
		List<Kpi> kpiInErrorRateRange = new ArrayList<>();
		kpiInErrorRateRange = kpiRepo.findByErrorRateBetween(low, high);
		
		if(kpiInErrorRateRange.size() > 0) {
			return new ResponseEntity<List<Kpi>>(kpiInErrorRateRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Kpi>>(kpiInErrorRateRange, HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping("/kpi/throughput_range")
	public ResponseEntity<List<Kpi>> getKpiByThroughputRange(@RequestParam double low, @RequestParam double high){
		List<Kpi> kpiInThroughputRange = new ArrayList<>();
		kpiInThroughputRange = kpiRepo.findByThroughputBetween(low, high);
		
		if(kpiInThroughputRange.size() > 0) {
			return new ResponseEntity<List<Kpi>>(kpiInThroughputRange, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Kpi>>(kpiInThroughputRange, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/latency_over_time")
	public ResponseEntity<List<Object[]>> getAllLatencyTimestamps(){
		List<Object[]> latencyTimestamps = new ArrayList<>();
		latencyTimestamps = kpiRepo.findLatencyTimestamps();
		
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
		errorRateTimestamps = kpiRepo.findErrorRateTimestamps();

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
		throughputTimestamps = kpiRepo.findThroughputTimestamps();

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
		l_er_t = kpiRepo.findAllLatencyErrorRateThroughput();	
		
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
		errorrate_vs_throughput = kpiRepo.findErrorVsThroughput();
		
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
		latency_vs_throughput = kpiRepo.findLatencyVsThroughput();
		
		if(latency_vs_throughput.size() > 0) {
			return new ResponseEntity<List<Object[]>>(latency_vs_throughput, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(latency_vs_throughput, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/top_ten_latency")
	public ResponseEntity<List<Kpi>> getTopTenNodes_Latency(){
		List<Kpi> top10_latency = new ArrayList<>();
		top10_latency = kpiRepo.findTop10Nodes_Latency();
		
		if(top10_latency.size() > 0) {
			return new ResponseEntity<List<Kpi>>(top10_latency, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Kpi>>(top10_latency, HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping("/kpi/avg_latency_per_network")
	public ResponseEntity<List<Object[]>> getAvgLatencyPerNetwork(){
		List<Object[]> avg_latency_per_network = new ArrayList<>();
		avg_latency_per_network = kpiRepo.findAvgLatencyPerNetwork();
		
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
		avg_error_rate_per_network = kpiRepo.findAvgErrorPerNetwork();
		
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
		avg_throughput_per_network = kpiRepo.findAvgThroughputPerNetwork();
		
		if(avg_throughput_per_network.size() > 0) {
			return new ResponseEntity<List<Object[]>>(avg_throughput_per_network, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Object[]>>(avg_throughput_per_network, HttpStatus.NO_CONTENT);
		}
	}
}
