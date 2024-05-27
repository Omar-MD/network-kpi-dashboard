package com.tools.dashboard.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kpi")
public class Kpi {
	@Id
	private int node_id;
	private int net_id;
	private double latency;
	private double throughput;
	private double errorRate;
	private LocalDateTime timestamp;

	public Kpi() {
		super();
	}

	public Kpi(int node_id, int net_id, double latency, double throughput, double error_rate, LocalDateTime timestamp) {
		super();
		this.node_id = node_id;
		this.net_id = net_id;
		this.latency = latency;
		this.throughput = throughput;
		this.errorRate = error_rate;
		this.timestamp = timestamp;
	}

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public int getNet_id() {
		return net_id;
	}

	public void setNet_id(int net_id) {
		this.net_id = net_id;
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

	public double getError_rate() {
		return errorRate;
	}

	public void setError_rate(double error_rate) {
		this.errorRate = error_rate;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
