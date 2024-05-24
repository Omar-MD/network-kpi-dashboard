package com.tools.subscriber.dto;

import java.time.LocalDateTime;

public class Subscriber {

    private int nodeId;
    private int networkId;
    private double latency;
    private double throughput;
    private double errorRate;
    private LocalDateTime timestamp;


    //Constructors, Getters
    public Subscriber(){}

    public Subscriber(int nodeId, int networkId, double latency, double throughput, double errorRate, LocalDateTime timestamp) {
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

    public int getNetworkId() {
        return networkId;
    }

    public double getLatency() {
        return latency;
    }

    public double getThroughput() {
        return throughput;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "nodeId=" + nodeId +
                ", networkId=" + networkId +
                ", latency=" + latency +
                ", throughput=" + throughput +
                ", errorRate=" + errorRate +
                ", timestamp=" + timestamp +
                '}';
    }

}//end of class
