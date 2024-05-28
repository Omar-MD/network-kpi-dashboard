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

    //Latency over time - select latency from table
    @Query("SELECT k.latency, k.timestamp FROM Kpi k")
    List<Object[]> findLatencyTimestamps();
    //error rate over time - select error_rate from table
    @Query("SELECT k.errorRate, k.timestamp FROM Kpi k")
    List<Object[]> findErrorRateTimestamps();
    //throughput over time - select throughput from table
    @Query("SELECT k.throughput, k.timestamp FROM Kpi k")
    List<Object[]> findThroughputTimestamps();
    //correlation matrix - latency, error rate, throughput - heatmap?
    @Query("SELECT k.latency, k.errorRate, k.throughput FROM Kpi k")
    List<Object[]> findAllLatencyErrorRateThroughput();

    //error rate vs throughput - scatter - select both
    @Query("SELECT k.errorRate, k.throughput FROM Kpi k")
    List<Object[]> findErrorVsThroughput();
    //latency vs throughput - scatter - select both
    @Query("SELECT k.latency, k.throughput FROM Kpi k")
    List<Object[]> findLatencyVsThroughput();
    //top n nodes by attributes - bar? - all three
    @Query("SELECT k FROM Kpi k ORDER BY k.latency DESC")
    List<Kpi> findTop10Nodes_Latency();
    //average latency per network
    @Query("SELECT k.net_id, avg(k.latency) from Kpi k GROUP BY k.net_id")
    List<Object[]> findAvgLatencyPerNetwork();
    //avg error rate per
    @Query("SELECT k.net_id, avg(k.errorRate) from Kpi k GROUP BY k.net_id")
    List<Object[]> findAvgErrorPerNetwork();
    //avg througjput per
    @Query("SELECT k.net_id, avg(k.throughput) from Kpi k GROUP BY k.net_id")
    List<Object[]> findAvgThroughputPerNetwork();

    //heatmaps
    //Mean LAtency
    //Mean Error Rate
    //Mean Throughput

}