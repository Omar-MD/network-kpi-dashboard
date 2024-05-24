package com.tools.publisher.dto;

import java.time.LocalDateTime;

public class PerformanceData {
    private int nodeId;
    private int networkId;
    private double latency;
    private double throughput;
    private double errorRate;
    private LocalDateTime timestamp;

    // Constructors, Getters, and Setters

    public PerformanceData() {}

    public PerformanceData(int nodeId, int networkId, double latency, double throughput, double errorRate, LocalDateTime timestamp) {
        this.nodeId = nodeId;
        this.networkId = networkId;
        this.latency = latency;
        this.throughput = throughput;
        this.errorRate = errorRate;
        this.timestamp = timestamp;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PerformanceData{" +
                "nodeId=" + nodeId +
                ", networkId=" + networkId +
                ", latency=" + latency +
                ", throughput=" + throughput +
                ", errorRate=" + errorRate +
                ", timestamp=" + timestamp +
                '}';
    }
}