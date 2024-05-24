package com.tools.subscriber;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="nodeData")
public class NodeData {
    @Id
    private int nodeId;
    private int networkId;
    private double latency;
    private double throughput;
    private double errorRate;
    private LocalDateTime timestamp;

    // Constructors, Getters, and Setters

    public NodeData() {
    }

    public NodeData(int nodeId, int networkId, double latency, double throughput, double errorRate,
            LocalDateTime timestamp) {
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
        return "NodeData{" +
                "nodeId=" + nodeId +
                ", networkId=" + networkId +
                ", latency=" + latency +
                ", throughput=" + throughput +
                ", errorRate=" + errorRate +
                ", timestamp=" + timestamp +
                '}';
    }
}