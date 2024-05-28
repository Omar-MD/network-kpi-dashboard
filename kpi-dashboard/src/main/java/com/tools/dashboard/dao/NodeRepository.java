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


    //AVerage LAtency - time series - moving average
    //Average Error Rate - time series - moving average
    //Average Throughput - time series - moving average
    //Node with highest latency
    //node with highest error rate
    //node with lowest throughput

    //Latency over time - select latency from table
    @Query("SELECT nd.latency, nd.timestamp FROM NodeData nd")
    List<Object[]> findLatencyTimestamps();
    //error rate over time - select error_rate from table
    @Query("SELECT nd.errorRate, nd.timestamp FROM NodeData nd")
    List<Object[]> findErrorRateTimestamps();
    //throughput over time - select throughput from table
    @Query("SELECT nd.throughput, nd.timestamp FROM NodeData nd")
    List<Object[]> findThroughputTimestamps();
    //correlation matrix - latency, error rate, throughput - heatmap?
    @Query("SELECT nd.latency, nd.errorRate, nd.throughput FROM NodeData nd")
    List<Object[]> findAllLatencyErrorRateThroughput();

    //error rate vs throughput - scatter - select both
    @Query("SELECT nd.errorRate, nd.throughput FROM NodeData nd")
    List<Object[]> findErrorVsThroughput();
    //latency vs throughput - scatter - select both
    @Query("SELECT nd.latency, nd.throughput FROM NodeData nd")
    List<Object[]> findLatencyVsThroughput();
    //top n nodes by attributes - bar? - all three
    @Query("SELECT nd FROM NodeData nd ORDER BY nd.latency DESC")
    List<NodeData> findTop10Nodes_Latency();
    //average latency per network
    @Query("SELECT nd.networkId, avg(nd.latency) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgLatencyPerNetwork();
    //avg error rate per
    @Query("SELECT nd.networkId, avg(nd.errorRate) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgErrorPerNetwork();
    //avg througjput per
    @Query("SELECT nd.networkId, avg(nd.throughput) from NodeData nd GROUP BY nd.networkId")
    List<Object[]> findAvgThroughputPerNetwork();

    //heatmaps
    //Mean LAtency
    //Mean Error Rate
    //Mean Throughput

}