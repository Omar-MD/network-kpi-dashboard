package com.tools.dashboard.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tools.dashboard.dto.NodeData;

@Repository
public interface NodeRepository extends JpaRepository<NodeData, Long>{
	List<NodeData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
	List<NodeData> findByLatencyBetween(double low, double high);
	List<NodeData> findByThroughputBetween(double low, double high);
	List<NodeData> findByErrorRateBetween(double low, double high);
	Optional<List<NodeData>> findByNodeId(int id);
	Optional<List<NodeData>> findByNetworkId(int id);

//    //New
    @Query("SELECT AVG(nd.latency), AVG(nd.errorRate), AVG(nd.throughput) FROM NodeData nd")
    List<Object[]> findAverageMetrics();

    @Query(value = "SELECT node_id, MAX(latency) AS max_latency, MAX(throughput) AS max_throughput, MAX(error_rate) AS max_error_rate " +
            "FROM Node_data " +
            "GROUP BY node_id " +
            "ORDER BY max_latency DESC, max_throughput DESC, max_error_rate DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findNodeWithHighestMetrics();

    @Query("SELECT nd.latency, nd.timestamp FROM NodeData nd")
    List<Object[]> findLatencyTimestamps();
    @Query("SELECT nd.errorRate, nd.timestamp FROM NodeData nd")
    List<Object[]> findErrorRateTimestamps();
    @Query("SELECT nd.throughput, nd.timestamp FROM NodeData nd")
    List<Object[]> findThroughputTimestamps();
    @Query("SELECT nd.latency, nd.errorRate, nd.throughput FROM NodeData nd")
    List<Object[]> findAllLatencyErrorRateThroughput();

    @Query("SELECT nd.errorRate, nd.throughput FROM NodeData nd")
    List<Object[]> findErrorVsThroughput();
    @Query("SELECT nd.latency, nd.throughput FROM NodeData nd")
    List<Object[]> findLatencyVsThroughput();
    @Query("SELECT nd FROM NodeData nd ORDER BY nd.latency DESC")
    List<NodeData> findTop10Nodes_Latency();
    @Query("SELECT nd.networkId, avg(nd.latency) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgLatencyPerNetwork();
    @Query("SELECT nd.networkId, avg(nd.errorRate) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgErrorPerNetwork();
    @Query("SELECT nd.networkId, avg(nd.throughput) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgThroughputPerNetwork();

    @Query(value = "SELECT node_id, MAX(throughput) AS highest_throughput FROM node_data GROUP BY node_id ORDER BY highest_throughput DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findNodeWithHighestThroughput();

    @Query(value = "SELECT node_id, MAX(latency) AS highest_latency FROM node_data GROUP BY node_id ORDER BY highest_latency DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findNodeWithHighestLatency();

    @Query(value = "SELECT node_id, MAX(error_rate) AS highest_error_rate FROM node_data GROUP BY node_id ORDER BY highest_error_rate DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findNodeWithHighestErrorRate();


}
