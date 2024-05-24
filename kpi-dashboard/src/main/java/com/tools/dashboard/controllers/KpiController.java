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
	
}
