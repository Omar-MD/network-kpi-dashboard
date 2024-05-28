package com.tools.dashboard.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tools.dashboard.dto.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Integer>{
	List<Kpi> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
	List<Kpi> findByLatencyBetween(double low, double high);
	List<Kpi> findByThroughputBetween(double low, double high);
	List<Kpi> findByErrorRateBetween(double low, double high);

	//New
	@Query("SELECT AVG(k.latency), AVG(k.errorRate), AVG(k.throughput) FROM Kpi k")
	List<Object[]> findAverageMetrics();

	@Query(value = "SELECT node_id, MAX(latency) AS max_latency, MAX(throughput) AS max_throughput, MAX(error_rate) AS max_error_rate " +
			"FROM kpi " +
			"GROUP BY node_id " +
			"ORDER BY max_latency DESC, max_throughput DESC, max_error_rate DESC " +
			"LIMIT 1", nativeQuery = true)
	List<Object[]> findNodeWithHighestMetrics();


	//AVerage LAtency - time series - moving average
//Average Error Rate - time series - moving average
//Average Throughput - time series - moving average
//Node with highest latency
//node with highest error rate
//node with lowest throughput


}