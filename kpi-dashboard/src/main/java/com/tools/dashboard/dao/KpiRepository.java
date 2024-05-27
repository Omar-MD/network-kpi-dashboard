package com.tools.dashboard.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tools.dashboard.dto.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Integer>{
	List<Kpi> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
	List<Kpi> findByLatencyBetween(double low, double high);
	List<Kpi> findByThroughputBetween(double low, double high);
	List<Kpi> findByErrorRateBetween(double low, double high);
	
	
	//AVerage LAtency - time series - moving average
	//Average Error Rate - time series - moving average
	//Average Throughput - time series - moving average
	//Node with highest latency 
	//node with highest error rate
	//node with lowest throughput
	
	//Latency over time - select latency from table
	//error rate over time - select error_rate from table
	//throughput over time - select throughput from table
	//correlation matrix - latency, error rate, throughput - heatmap - select latency, errorrate throughput from table
	//error rate vs throughput - scatter - select both
	//latency vs throughput - scatter - select both
	//top n nodes by attributes - bar - all three - select, order by, limit

	
	
	
	//heatmaps
	//Mean LAtency
	//Mean Error Rate
	//Mean Throughput
	
	
}